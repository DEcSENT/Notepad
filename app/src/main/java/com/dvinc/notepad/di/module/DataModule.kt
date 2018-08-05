/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/14/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.di.module

import com.dvinc.notepad.data.repository.MarkerDataRepository
import com.dvinc.notepad.data.repository.NoteDataRepository
import com.dvinc.notepad.domain.repository.MarkerRepository
import com.dvinc.notepad.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun provideNoteRepository(repository: NoteDataRepository): NoteRepository


    @Binds
    fun provideMarkerRepository(repository: MarkerDataRepository): MarkerRepository
}
