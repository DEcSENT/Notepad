/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad

import android.app.Application
import com.dvinc.notepad.di.DiProvider
import com.facebook.stetho.Stetho

class NotepadApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DiProvider.buildDi(this)

        Stetho.initializeWithDefaults(this)
    }
}
