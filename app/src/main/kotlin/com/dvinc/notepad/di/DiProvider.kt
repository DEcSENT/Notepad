/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.di

import android.content.Context
import com.dvinc.notepad.di.component.AppComponent
import com.dvinc.notepad.di.module.AppModule

object DiProvider {

    lateinit var appComponent: AppComponent

    fun buildDi(context: Context) {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context))
            .build()
    }
}
