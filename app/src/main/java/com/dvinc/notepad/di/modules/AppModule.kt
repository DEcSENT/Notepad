/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.dvinc.notepad.data.database.NotepadDatabase
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

    @Provides
    fun providesAppContext() = context

    @Provides
    fun providesAppDatabase(context: Context): NotepadDatabase =
            Room.databaseBuilder(context, NotepadDatabase::class.java, "notepad").build()
}
