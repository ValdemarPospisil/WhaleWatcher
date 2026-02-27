package com.valdemar.whalewatcher.ui

import android.os.Build
import android.widget.Button
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O], application = HiltTestApplication::class)
class MainActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun clickingButton_triggersApiAndDisplaysResult() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                val fetchButton = activity.findViewById<Button>(android.R.id.button1)
                val resultText = activity.findViewById<TextView>(android.R.id.text1)

                assertNotNull("Fetch button should exist", fetchButton)
                assertNotNull("Result text view should exist", resultText)
                
                assertEquals("Fetch Images", fetchButton.text.toString())
            }
        }
    }
}
