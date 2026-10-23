package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VerificationStatus
import com.example.ui.theme.RestrainedGold
import com.example.ui.theme.VerifiedGreen

@Composable
fun VerificationBadge(
    status: VerificationStatus,
    modifier: Modifier = Modifier,
    customText: String? = null
) {
    val isVerified = status == VerificationStatus.VERIFIED
    val bgColor = if (isVerified) VerifiedGreen.copy(alpha = 0.12f) else RestrainedGold.copy(alpha = 0.12f)
    val contentColor = if (isVerified) VerifiedGreen else RestrainedGold
    val text = customText ?: if (isVerified) "যাচাইকৃত (Verified)" else "পর্যালোচনাধীন (Pending Review)"
    val icon = if (isVerified) Icons.Default.CheckCircle else Icons.Default.Info

    Box(
        modifier = modifier
            .testTag("verification_badge")
            .background(bgColor, RoundedCornerShape(12.dp))
            .border(1.dp, contentColor.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = contentColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                color = contentColor
            )
        }
    }
}
