/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.dvinc.notepad.common.timber.ReleaseTree
import com.dvinc.notepad.di.DiProvider
import com.facebook.stetho.Stetho
import timber.log.Timber

class NotepadApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DiProvider.buildDi(this)

        Stetho.initializeWithDefaults(this)

        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else ReleaseTree())

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
}
