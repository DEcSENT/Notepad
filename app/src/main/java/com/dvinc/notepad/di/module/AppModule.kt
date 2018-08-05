/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.dvinc.notepad.data.database.NotepadDatabase
import com.dvinc.notepad.data.database.dao.NoteDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    companion object {
        private const val DATABASE_NAME = "notepad"
    }

    @Provides
    fun providesAppContext() = context

    @Provides
    @Singleton
    fun providesAppDatabase(context: Context): NotepadDatabase =
            Room.databaseBuilder(context, NotepadDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideNoteDao(database: NotepadDatabase): NoteDao {
        return database.notesDao()
    }
}