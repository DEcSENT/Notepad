/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.core.di.module

import android.content.Context
import androidx.room.Room
import com.dvinc.core.database.NotepadDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    companion object {

        private const val DATABASE_NAME = "notepad"
    }

    @Provides
    @Singleton
    fun providesAppDatabase(context: Context): NotepadDatabase {
        return Room.databaseBuilder(
            context,
            NotepadDatabase::class.java,
            DATABASE_NAME
        )
            .build()
    }
}
