package com.valdemar.whalewatcher.ui

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
        }

        val fetchButton = Button(this).apply {
            id = android.R.id.button1
            text = "Fetch Images"
            setOnClickListener { viewModel.fetchImages() }
        }

        val resultText = TextView(this).apply {
            id = android.R.id.text1
            text = "Click to fetch from Docker Hub"
            textSize = 18f
            gravity = Gravity.CENTER
            setPadding(0, 32, 0, 0)
        }

        rootLayout.addView(fetchButton)
        rootLayout.addView(resultText)

        setContentView(rootLayout)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is MainUiState.Idle -> {
                            resultText.text = "Click to fetch from Docker Hub"
                        }
                        is MainUiState.Loading -> {
                            resultText.text = "Loading..."
                        }
                        is MainUiState.Success -> {
                            resultText.text = "First Image: \${state.imageName}"
                        }
                        is MainUiState.Error -> {
                            resultText.text = "Error: \${state.message}"
                            Log.e("MainActivity", "API Error: \${state.message}")
                        }
                    }
                }
            }
        }
    }
}
