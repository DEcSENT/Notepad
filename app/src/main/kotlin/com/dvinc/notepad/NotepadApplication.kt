/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.dvinc.core.di.DaggerApplication
import com.dvinc.core.di.provider.ApplicationProvider
import com.dvinc.core.timber.ReleaseTree
import com.dvinc.notepad.di.component.AppComponent
import com.facebook.stetho.Stetho
import timber.log.Timber

class NotepadApplication : Application(), DaggerApplication {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        buildDi()

        Stetho.initializeWithDefaults(this)

        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else ReleaseTree())

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    override fun getApplicationProvider(): ApplicationProvider {
        return appComponent
    }

    private fun buildDi() {
        appComponent = AppComponent.Builder.build(this)
    }
}
