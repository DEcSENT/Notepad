/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad

import android.app.Application
import com.dvinc.notepad.di.components.AppComponent
import com.dvinc.notepad.di.components.DaggerAppComponent
import com.dvinc.notepad.di.modules.AppModule

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = buildDi()
    }

    private fun buildDi() = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build();
}
