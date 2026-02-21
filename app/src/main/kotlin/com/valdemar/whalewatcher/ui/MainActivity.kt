package com.valdemar.whalewatcher.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView =
            TextView(this).apply {
                text = "WhaleWatcher Scaffolding Initialized!"
                textSize = 24f
                setPadding(32, 32, 32, 32)
            }

        setContentView(textView)
    }
}
