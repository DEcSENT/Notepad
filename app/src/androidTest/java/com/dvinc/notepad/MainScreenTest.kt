/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad

import android.content.Context
import android.util.DisplayMetrics
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.dvinc.notepad.presentation.ui.main.MainActivity
import com.facebook.testing.screenshot.Screenshot
import com.facebook.testing.screenshot.ViewHelpers
import org.junit.Rule
import org.junit.Test

class MainScreenTest {

    @get:Rule
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java, false, false)

    private val context = getInstrumentation().targetContext

    private val contextWrapper = ContextThemeWrapper(context, R.style.AppTheme)

    private val inflater = LayoutInflater.from(contextWrapper)

    @Test
    fun testEmptyNotepadList() {
        val activity = activityTestRule.launchActivity(null)

        Screenshot
            .snapActivity(activity)
            .record()
    }

    @Test
    fun testEmptyNoteFragment() {
        val noteFragment = inflater.inflate(R.layout.fragment_note, null, false)

        compareScreenshot(noteFragment)
    }

    private fun compareScreenshot(view: View) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        ViewHelpers.setupView(view)
            .setExactHeightPx(metrics.heightPixels)
            .setExactWidthPx(metrics.widthPixels)
            .layout()

        Screenshot
            .snap(view)
            .record()
    }
}
