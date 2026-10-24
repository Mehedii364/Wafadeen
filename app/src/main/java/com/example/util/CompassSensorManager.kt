package com.example.util

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.abs

data class CompassData(
    val azimuth: Float = 0f,
    val pitch: Float = 0f,
    val roll: Float = 0f,
    val accuracy: Int = SensorManager.SENSOR_STATUS_ACCURACY_HIGH,
    val accuracyLabelBn: String = "উচ্চ নির্ভুলতা",
    val hasSensors: Boolean = false,
    val sensorTypeName: String = "অপেক্ষমাণ...",
    val isFlatEnough: Boolean = true
)

class CompassSensorManager(private val context: Context) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        vibratorManager?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    private val _compassData = MutableStateFlow(CompassData())
    val compassData: StateFlow<CompassData> = _compassData.asStateFlow()

    private var rotationVectorSensor: Sensor? = null
    private var accelerometerSensor: Sensor? = null
    private var magneticSensor: Sensor? = null
    private var orientationSensor: Sensor? = null

    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)
    private val lastAccelerometer = FloatArray(3)
    private val lastMagnetometer = FloatArray(3)
    private var hasAccelerometerData = false
    private var hasMagnetometerData = false

    private var currentFilteredAzimuth = 0f
    private var isAlignedPreviously = false
    var isVibrationEnabled: Boolean = true

    init {
        rotationVectorSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        if (rotationVectorSensor == null) {
            accelerometerSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            magneticSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        }
        @Suppress("DEPRECATION")
        orientationSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION)
    }

    fun startListening() {
        val sm = sensorManager ?: run {
            _compassData.value = _compassData.value.copy(
                hasSensors = false,
                sensorTypeName = "সেন্সর অনুপস্থিত (সিমুলেশন মোড)"
            )
            return
        }

        hasAccelerometerData = false
        hasMagnetometerData = false

        if (rotationVectorSensor != null) {
            sm.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_UI)
            _compassData.value = _compassData.value.copy(
                hasSensors = true,
                sensorTypeName = "হার্ডওয়্যার রোটেশন ভেক্টর"
            )
        } else if (accelerometerSensor != null && magneticSensor != null) {
            sm.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI)
            sm.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_UI)
            _compassData.value = _compassData.value.copy(
                hasSensors = true,
                sensorTypeName = "ম্যাগনেটোমিটার + এক্সিলারোমিটার"
            )
        } else if (orientationSensor != null) {
            sm.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_UI)
            _compassData.value = _compassData.value.copy(
                hasSensors = true,
                sensorTypeName = "ডিজিটাল ওরিয়েন্টেশন সেন্সর"
            )
        } else {
            _compassData.value = _compassData.value.copy(
                hasSensors = false,
                sensorTypeName = "সেন্সর অনুপস্থিত (সিমুলেশন মোড)"
            )
        }
    }

    fun stopListening() {
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        var rawAzimuth: Float? = null
        var pitch = 0f
        var roll = 0f

        when (event.sensor.type) {
            Sensor.TYPE_ROTATION_VECTOR -> {
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                SensorManager.getOrientation(rotationMatrix, orientationAngles)
                val azRad = orientationAngles[0]
                pitch = Math.toDegrees(orientationAngles[1].toDouble()).toFloat()
                roll = Math.toDegrees(orientationAngles[2].toDouble()).toFloat()
                rawAzimuth = (Math.toDegrees(azRad.toDouble()).toFloat() + 360f) % 360f
            }
            Sensor.TYPE_ACCELEROMETER -> {
                System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.size)
                hasAccelerometerData = true
                if (hasMagnetometerData) {
                    val success = SensorManager.getRotationMatrix(rotationMatrix, null, lastAccelerometer, lastMagnetometer)
                    if (success) {
                        SensorManager.getOrientation(rotationMatrix, orientationAngles)
                        pitch = Math.toDegrees(orientationAngles[1].toDouble()).toFloat()
                        roll = Math.toDegrees(orientationAngles[2].toDouble()).toFloat()
                        rawAzimuth = (Math.toDegrees(orientationAngles[0].toDouble()).toFloat() + 360f) % 360f
                    }
                }
            }
            Sensor.TYPE_MAGNETIC_FIELD -> {
                System.arraycopy(event.values, 0, lastMagnetometer, 0, event.values.size)
                hasMagnetometerData = true
                if (hasAccelerometerData) {
                    val success = SensorManager.getRotationMatrix(rotationMatrix, null, lastAccelerometer, lastMagnetometer)
                    if (success) {
                        SensorManager.getOrientation(rotationMatrix, orientationAngles)
                        pitch = Math.toDegrees(orientationAngles[1].toDouble()).toFloat()
                        roll = Math.toDegrees(orientationAngles[2].toDouble()).toFloat()
                        rawAzimuth = (Math.toDegrees(orientationAngles[0].toDouble()).toFloat() + 360f) % 360f
                    }
                }
            }
            @Suppress("DEPRECATION")
            Sensor.TYPE_ORIENTATION -> {
                rawAzimuth = (event.values[0] + 360f) % 360f
                pitch = event.values[1]
                roll = event.values[2]
            }
        }

        if (rawAzimuth != null) {
            // Smooth circular interpolation
            var diff = (rawAzimuth - currentFilteredAzimuth) % 360f
            if (diff < -180f) diff += 360f
            if (diff > 180f) diff -= 360f
            currentFilteredAzimuth = (currentFilteredAzimuth + diff * 0.22f + 360f) % 360f

            val isFlat = abs(pitch) < 35f && abs(roll) < 35f

            _compassData.value = _compassData.value.copy(
                azimuth = currentFilteredAzimuth,
                pitch = pitch,
                roll = roll,
                hasSensors = true,
                isFlatEnough = isFlat
            )
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        val label = when (accuracy) {
            SensorManager.SENSOR_STATUS_ACCURACY_HIGH -> "উচ্চ নির্ভুলতা (High)"
            SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM -> "মধ্যম নির্ভুলতা (Medium)"
            SensorManager.SENSOR_STATUS_ACCURACY_LOW -> "স্বল্প নির্ভুলতা (ক্যালিব্রেট করুন)"
            SensorManager.SENSOR_STATUS_UNRELIABLE -> "অনির্ভরযোগ্য (ক্যালিব্রেট প্রয়োজন)"
            else -> "সাধারণ"
        }
        _compassData.value = _compassData.value.copy(
            accuracy = accuracy,
            accuracyLabelBn = label
        )
    }

    /**
     * Call this when evaluating alignment with Qibla to trigger haptic feedback once per alignment entry.
     */
    fun checkAlignmentFeedback(qiblaBearing: Double) {
        val azimuth = _compassData.value.azimuth
        val relative = (qiblaBearing.toFloat() - azimuth + 360f) % 360f
        val diff = if (relative > 180f) 360f - relative else relative
        val isAligned = diff <= 3.0f

        if (isAligned && !isAlignedPreviously) {
            triggerHapticPulse()
            isAlignedPreviously = true
        } else if (diff > 5.0f) {
            isAlignedPreviously = false
        }
    }

    private fun triggerHapticPulse() {
        if (!isVibrationEnabled) return
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(
                    VibrationEffect.createWaveform(
                        longArrayOf(0, 45, 60, 50),
                        intArrayOf(0, 200, 0, 255),
                        -1
                    )
                )
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(50L)
            }
        } catch (e: Exception) {
            // Ignored if device has no vibrator or security restriction
        }
    }

    fun setManualAzimuth(simulatedAzimuth: Float) {
        currentFilteredAzimuth = (simulatedAzimuth + 360f) % 360f
        _compassData.value = _compassData.value.copy(
            azimuth = currentFilteredAzimuth,
            hasSensors = false,
            sensorTypeName = "ম্যানুয়াল সিমুলেশন",
            accuracyLabelBn = "সিমুলেশন টেস্ট মোড"
        )
    }
}
