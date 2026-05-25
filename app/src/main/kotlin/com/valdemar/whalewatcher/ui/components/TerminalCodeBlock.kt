package com.valdemar.whalewatcher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun TerminalCodeBlock(
    command: String,
    modifier: Modifier = Modifier,
    onCopyClick: () -> Unit = {}
) {
    // A dark, high-contrast block representing a terminal
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF0F172A)) // Always dark, even in light theme, for the "terminal" look
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color(0xFF38BDF8))) {
                        append("> ")
                    }
                    withStyle(style = SpanStyle(color = Color(0xFFF8FAFC))) {
                        append(command)
                    }
                },
                style = MaterialTheme.typography.labelLarge, // Monospace
                modifier = Modifier.weight(1f)
            )
            
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = "Copy to clipboard",
                tint = Color(0xFF94A3B8),
                modifier = Modifier
                    .clickable(onClick = onCopyClick)
                    .padding(4.dp)
            )
        }
    }
}
