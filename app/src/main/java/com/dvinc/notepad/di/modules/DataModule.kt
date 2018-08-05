/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/14/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.di.modules

import com.dvinc.notepad.data.database.NotepadDatabase
import com.dvinc.notepad.data.repository.MarkerDataRepository
import com.dvinc.notepad.data.repository.NoteDataRepository
import com.dvinc.notepad.domain.repository.MarkerRepository
import com.dvinc.notepad.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideNotesRepository(
            database: NotepadDatabase
    ): NoteRepository = NoteDataRepository(database)

    @Provides
    fun providesMarkersRepository(): MarkerRepository = MarkerDataRepository()
}
