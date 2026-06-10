package com.valdemar.whalewatcher.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun MarkdownDescription(markdown: String, modifier: Modifier = Modifier) {
    // MarkdownText from jeziellago/compose-markdown doesn't have an easy way to override specific heading styles
    // via its API. Instead of complex overrides, wait, let's look at MarkdownText.
    // compose-markdown uses coil internally or text styles. But wait, `MarkdownText` has a `style` parameter.
    // Actually, according to compose-markdown, headings are hardcoded in Markwon or rendered natively.
    // The easiest way to downscale is to literally replace the markdown headings in the raw text, OR
    // wait, we can just replace "# " with "### " in the text if we want to scale them down, but that's hacky.
    // Is there a MarkdownText configuration for Typography? Let's check.
    
    // For now, since Markwon/compose-markdown uses the `style` parameter for body, 
    // downscaling headings can be done by preprocessing the text to increase the heading level by 2.
    // e.g. H1 (#) -> H3 (###), H2 (##) -> H4 (####) etc.
    // This perfectly matches "H1 behaves like H3, H2 behaves like H4"
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
