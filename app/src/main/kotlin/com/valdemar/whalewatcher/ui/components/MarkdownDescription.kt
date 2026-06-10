package com.valdemar.whalewatcher.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun MarkdownDescription(markdown: String, modifier: Modifier = Modifier) {
    val downscaledMarkdown = scaleDownHeadings(markdown)

    MarkdownText(
        markdown = downscaledMarkdown,
        modifier = modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        )
    )
}

/**
 * Pre-processes the markdown string to increase the level of headings by 2.
 * E.g. # becomes ###, ## becomes ####. 
 * Caps at ###### (H6) since markdown doesn't support H7+.
 */
internal fun scaleDownHeadings(text: String): String {
    return text.lineSequence().joinToString("\n") { line ->
        if (line.startsWith("#")) {
            val hashCount = line.takeWhile { it == '#' }.length
            if (hashCount > 0) {
                // We only want to process actual headings where '#' is followed by a space
                if (line.getOrNull(hashCount) == ' ') {
                    val newHashCount = (hashCount + 2).coerceAtMost(6)
                    val newHashes = "#".repeat(newHashCount)
                    newHashes + line.substring(hashCount)
                } else {
                    line
                }
            } else {
                line
            }
        } else {
            line
        }
    }
}
