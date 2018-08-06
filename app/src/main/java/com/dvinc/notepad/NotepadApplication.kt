/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad

import android.app.Application
import com.dvinc.notepad.di.component.AppComponent
import com.dvinc.notepad.di.component.DaggerAppComponent
import com.dvinc.notepad.di.module.AppModule
import com.facebook.stetho.Stetho

class NotepadApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = buildDi()

        Stetho.initializeWithDefaults(this)
    }

    private fun buildDi() = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
}
