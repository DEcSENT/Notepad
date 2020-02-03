/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.dvinc.core.timber.ReleaseTree
import com.dvinc.notepad.di.component.AppComponent
import com.dvinc.notepad.di.component.DaggerAppComponent
import com.dvinc.notepad.di.module.AppModule
import com.facebook.stetho.Stetho
import timber.log.Timber

class NotepadApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        buildDi(this)

        Stetho.initializeWithDefaults(this)

        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else ReleaseTree())

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    private fun buildDi(context: Context) {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context))
            .build()
    }
}
