/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.di.modules

import com.dvinc.notepad.common.rxschedulers.RxSchedulers
import com.dvinc.notepad.data.repositories.MarkersRepository
import com.dvinc.notepad.data.repositories.NotesRepository
import com.dvinc.notepad.domain.interactors.NotesInteractor
import com.dvinc.notepad.domain.interactors.NotesInteractorImpl
import com.dvinc.notepad.domain.mappers.NoteMapper
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun provideNotesInteractor(
            notesRepository: NotesRepository,
            markersRepository: MarkersRepository,
            noteMapper: NoteMapper,
            rxSchedulers: RxSchedulers
    ): NotesInteractor = NotesInteractorImpl(notesRepository, markersRepository, noteMapper, rxSchedulers)
}
