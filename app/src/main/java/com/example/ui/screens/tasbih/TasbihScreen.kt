package com.example.ui.screens.tasbih

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.TasbihDao
import com.example.data.local.TasbihEntity
import com.example.data.model.TasbihDhikr
import com.example.repository.TasbihRepository
import com.example.ui.components.SectionHeader
import com.example.ui.theme.DeepEmerald
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.RestrainedGold
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TasbihScreen(
    tasbihDao: TasbihDao,
    modifier: Modifier = Modifier
) {
    val repository = remember { TasbihRepository(tasbihDao) }
    val historyList by repository.history.collectAsState(initial = emptyList())

    val dhikrs = repository.availableDhikrs
    var selectedDhikr by remember { mutableStateOf(dhikrs[0]) }
    var count by remember { mutableIntStateOf(0) }
    var target by remember { mutableIntStateOf(selectedDhikr.defaultTarget) }
    var isVibrationOn by remember { mutableStateOf(true) }
    var showResetDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val vibrator = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vm?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
    }

    fun triggerVibration() {
        if (!isVibrationOn || vibrator == null || !vibrator.hasVibrator()) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(40)
        }
    }

    val progress = if (target > 0) (count.toFloat() / target).coerceIn(0f, 1f) else 0f
    val animatedProgress by animateFloatAsState(targetValue = progress, animationSpec = tween(200), label = "tasbih_progress")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("tasbih_screen"),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Dhikr Selector Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(dhikrs) { dhikr ->
                    FilterChip(
                        selected = selectedDhikr.id == dhikr.id,
                        onClick = {
                            selectedDhikr = dhikr
                            target = dhikr.defaultTarget
                            count = 0
                        },
                        label = { Text(dhikr.transliterationBn) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }

        // Active Dhikr Details Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        RestrainedGold.copy(alpha = 0.3f),
                        RoundedCornerShape(16.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = selectedDhikr.arabic,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = RestrainedGold,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = selectedDhikr.meaningBn,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "ফজিলত: ${selectedDhikr.virtueBn}",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Target Selector Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "টার্গেট নির্ধারণ:",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(33, 99, 100, 1000).forEach { tgt ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (target == tgt) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                                )
                                .clickable { target = tgt }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "$tgt",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (target == tgt) FontWeight.Bold else FontWeight.Normal,
                                    color = if (target == tgt) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            }
        }

        // Huge Interactive Tasbih Counter Button
        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(240.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                DeepEmerald
                            )
                        )
                    )
                    .border(4.dp, RestrainedGold.copy(alpha = 0.5f), CircleShape)
                    .clickable {
                        count++
                        triggerVibration()
                        if (count == target) {
                            Toast.makeText(context, "আলহামদুলিল্লাহ! টার্গেট সম্পন্ন হয়েছে।", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .testTag("tasbih_counter_button")
            ) {
                // Circular progress ring
                CircularProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 6.dp,
                    color = RestrainedGold,
                    trackColor = Color.White.copy(alpha = 0.15f)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$count",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 54.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "টার্গেট: $target",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = RestrainedGold,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "গণনা করতে স্পর্শ করুন",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }

        // Controls: Decrement (-), Vibration toggle, Save session, Reset
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Decrement
                IconButton(
                    onClick = {
                        if (count > 0) {
                            count--
                            triggerVibration()
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                        .testTag("tasbih_decrement_button")
                ) {
                    Icon(imageVector = Icons.Default.Remove, contentDescription = "এক কমান", tint = MaterialTheme.colorScheme.onSurface)
                }

                // Vibration Toggle
                IconButton(
                    onClick = { isVibrationOn = !isVibrationOn },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            if (isVibrationOn) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                            CircleShape
                        )
                        .testTag("tasbih_vibration_toggle")
                ) {
                    Icon(
                        imageVector = Icons.Default.Vibration,
                        contentDescription = "ভাইব্রেশন",
                        tint = if (isVibrationOn) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Save Session to Database
                IconButton(
                    onClick = {
                        if (count > 0) {
                            val sdf = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
                            val dateStr = sdf.format(Date())
                            scope.launch {
                                repository.saveSession(
                                    dhikrTitle = selectedDhikr.transliterationBn,
                                    count = count,
                                    target = target,
                                    formattedDate = dateStr
                                )
                                Toast.makeText(context, "আমল সংরক্ষিত হয়েছে", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "সংরক্ষণ করার মতো কোনো গণনা নেই", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(RestrainedGold.copy(alpha = 0.2f), CircleShape)
                        .testTag("tasbih_save_button")
                ) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = "আমল সংরক্ষণ", tint = RestrainedGold)
                }

                // Reset
                IconButton(
                    onClick = { showResetDialog = true },
                    modifier = Modifier
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                        .testTag("tasbih_reset_button")
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "রিসেট", tint = MaterialTheme.colorScheme.error)
                }
            }
        }

        // Saved Sessions History
        item {
            SectionHeader(
                title = "সংরক্ষিত জিকিরের ইতিহাস",
                actionText = if (historyList.isNotEmpty()) "সব মুছুন" else null,
                onActionClick = {
                    scope.launch { repository.clearHistory() }
                }
            )
        }

        if (historyList.isEmpty()) {
            item {
                Text(
                    text = "এখনও কোনো ইতিহাস সংরক্ষণ করা হয়নি। জিকির সম্পন্ন করে সেভ বাটনে চাপুন।",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        } else {
            items(historyList, key = { it.id }) { session ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = session.dhikrTitle,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = session.formattedDate,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = "${session.count} বার",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = RestrainedGold
                        )
                    }
                }
            }
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("কাউন্টার রিসেট") },
            text = { Text("আপনি কি বর্তমান গণনা শূন্যে ফিরিয়ে নিতে চান?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        count = 0
                        showResetDialog = false
                    }
                ) {
                    Text("হ্যাঁ, রিসেট করুন", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("বাতিল")
                }
            }
        )
    }
}
