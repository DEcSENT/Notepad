/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.dvinc.notepad.data.database.NotepadDatabase
import com.dvinc.notepad.di.qualifiers.IoScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.Scheduler
import com.dvinc.notepad.di.qualifiers.UiScheduler
import io.reactivex.schedulers.Schedulers

@Module
class AppModule(private val context: Context) {

    @Provides
    fun providesAppContext() = context

    @Provides
    fun providesAppDatabase(context: Context): NotepadDatabase =
            Room.databaseBuilder(context, NotepadDatabase::class.java, "notepad").build()

    @Provides
    @IoScheduler
    fun provideIoScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @UiScheduler
    fun provideUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
