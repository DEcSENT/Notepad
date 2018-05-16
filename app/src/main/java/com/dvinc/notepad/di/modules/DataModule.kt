/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/14/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.di.modules

import com.dvinc.notepad.data.database.NotepadDatabase
import com.dvinc.notepad.data.repositories.MarkersRepository
import com.dvinc.notepad.data.repositories.NotesRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideNotesRepository(
            database: NotepadDatabase
    ): NotesRepository = NotesRepository(database)

    @Provides
    fun providesMarkersRepository(): MarkersRepository = MarkersRepository()
}
