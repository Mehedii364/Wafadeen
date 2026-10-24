package com.example.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.util.Locale
import java.util.TimeZone

data class LiveLocationState(
    val latitude: Double = 23.8103, // default Dhaka
    val longitude: Double = 90.4125,
    val accuracy: Float = 0f,
    val isLiveGps: Boolean = false,
    val locationNameBn: String = "ঢাকা (ডিফল্ট)",
    val locationNameEn: String = "Dhaka (Default)",
    val timeZoneOffsetHours: Double = 6.0,
    val lastUpdatedMillis: Long = System.currentTimeMillis(),
    val errorMessage: String? = null
)

class DeviceLocationManager(private val context: Context) {

    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager

    private val _locationState = MutableStateFlow(
        LiveLocationState(
            timeZoneOffsetHours = (TimeZone.getDefault().getOffset(System.currentTimeMillis()).toDouble() / 3600000.0)
        )
    )
    val locationState: StateFlow<LiveLocationState> = _locationState.asStateFlow()

    private var activeListener: LocationListener? = null

    fun hasLocationPermission(): Boolean {
        val finePerm = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarsePerm = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return finePerm || coarsePerm
    }

    fun isGpsProviderEnabled(): Boolean {
        return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true ||
                locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
    }

    /**
     * Attempts to read cached location immediately, then requests fresh fix
     */
    suspend fun refreshLocation(onComplete: ((Boolean) -> Unit)? = null) {
        if (!hasLocationPermission()) {
            _locationState.value = _locationState.value.copy(
                errorMessage = "লোকেশন অনুমতি প্রদান করা হয়নি। দয়া করে অনুমতি দিন।"
            )
            onComplete?.invoke(false)
            return
        }

        val lm = locationManager
        if (lm == null) {
            _locationState.value = _locationState.value.copy(
                errorMessage = "লোকেশন সার্ভিস পাওয়া যায়নি।"
            )
            onComplete?.invoke(false)
            return
        }

        // Try getting last known location first from GPS then Network
        var bestLocation: Location? = null
        try {
            val providers = listOf(
                LocationManager.GPS_PROVIDER,
                LocationManager.NETWORK_PROVIDER,
                LocationManager.PASSIVE_PROVIDER
            )
            for (provider in providers) {
                if (lm.isProviderEnabled(provider)) {
                    val loc = lm.getLastKnownLocation(provider)
                    if (loc != null) {
                        if (bestLocation == null || loc.accuracy < bestLocation.accuracy || loc.time > bestLocation.time) {
                            bestLocation = loc
                        }
                    }
                }
            }
        } catch (e: SecurityException) {
            // Permission revoked or not granted
        }

        if (bestLocation != null) {
            updateLocationState(bestLocation)
            onComplete?.invoke(true)
        }

        // Request a fresh update
        requestFreshLocationUpdate(onComplete)
    }

    private fun requestFreshLocationUpdate(onComplete: ((Boolean) -> Unit)? = null) {
        val lm = locationManager ?: return
        if (!hasLocationPermission()) return

        stopLocationUpdates()

        val listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                updateLocationState(location)
                stopLocationUpdates()
                onComplete?.invoke(true)
            }

            @Deprecated("Deprecated in Java")
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
        activeListener = listener

        try {
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                lm.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    2000L,
                    5f,
                    listener,
                    Looper.getMainLooper()
                )
            }
            if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                lm.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    2000L,
                    10f,
                    listener,
                    Looper.getMainLooper()
                )
            }
        } catch (e: SecurityException) {
            // Ignored
        }
    }

    fun stopLocationUpdates() {
        activeListener?.let { listener ->
            try {
                locationManager?.removeUpdates(listener)
            } catch (e: Exception) {
                // Ignored
            }
            activeListener = null
        }
    }

    private fun updateLocationState(location: Location) {
        val lat = location.latitude
        val lng = location.longitude
        val tzHours = TimeZone.getDefault().getOffset(System.currentTimeMillis()).toDouble() / 3600000.0

        val formattedCoords = "${String.format(Locale.US, "%.4f", lat)}° N, ${String.format(Locale.US, "%.4f", lng)}° E"
        val fallbackNameBn = "বর্তমান অবস্থান ($formattedCoords)"
        val fallbackNameEn = "Current Location ($formattedCoords)"

        _locationState.value = LiveLocationState(
            latitude = lat,
            longitude = lng,
            accuracy = location.accuracy,
            isLiveGps = true,
            locationNameBn = fallbackNameBn,
            locationNameEn = fallbackNameEn,
            timeZoneOffsetHours = tzHours,
            lastUpdatedMillis = System.currentTimeMillis(),
            errorMessage = null
        )

        // Try geocoding city name asynchronously
        resolveCityName(lat, lng)
    }

    private fun resolveCityName(latitude: Double, longitude: Double) {
        if (!Geocoder.isPresent()) return

        try {
            val geocoder = Geocoder(context, Locale("bn"))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                    if (addresses.isNotEmpty()) {
                        val addr = addresses[0]
                        val city = addr.locality ?: addr.subAdminArea ?: addr.adminArea ?: addr.countryName
                        if (!city.isNullOrBlank()) {
                            _locationState.value = _locationState.value.copy(
                                locationNameBn = "$city (লাইভ জিপিএস)",
                                locationNameEn = "${addr.locality ?: city} (Live GPS)"
                            )
                        }
                    }
                }
            } else {
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val addr = addresses[0]
                    val city = addr.locality ?: addr.subAdminArea ?: addr.adminArea ?: addr.countryName
                    if (!city.isNullOrBlank()) {
                        _locationState.value = _locationState.value.copy(
                            locationNameBn = "$city (লাইভ জিপিএস)",
                            locationNameEn = "${addr.locality ?: city} (Live GPS)"
                        )
                    }
                }
            }
        } catch (e: Exception) {
            // Geocoder service may not be available on emulators or without network
        }
    }

    fun setFallbackCity(
        cityNameBn: String,
        cityNameEn: String,
        latitude: Double,
        longitude: Double,
        tzOffset: Double
    ) {
        stopLocationUpdates()
        _locationState.value = LiveLocationState(
            latitude = latitude,
            longitude = longitude,
            accuracy = 0f,
            isLiveGps = false,
            locationNameBn = cityNameBn,
            locationNameEn = cityNameEn,
            timeZoneOffsetHours = tzOffset,
            lastUpdatedMillis = System.currentTimeMillis(),
            errorMessage = null
        )
    }
}
