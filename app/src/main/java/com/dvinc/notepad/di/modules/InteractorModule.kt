/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.di.modules

import com.dvinc.notepad.domain.interactors.NotepadInteractor
import com.dvinc.notepad.domain.interactors.NotesInteractor
import com.dvinc.notepad.domain.mappers.NoteMapper
import com.dvinc.notepad.domain.repositories.MarkersRepository
import com.dvinc.notepad.domain.repositories.NotesRepository
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun provideNotesInteractor(
            notesRepository: NotesRepository,
            markersRepository: MarkersRepository,
            noteMapper: NoteMapper
    ): NotesInteractor = NotesInteractor(notesRepository, markersRepository, noteMapper)

    @Provides
    fun provideNotepadInteractor(
            notesRepository: NotesRepository,
            markersRepository: MarkersRepository,
            noteMapper: NoteMapper
    ): NotepadInteractor = NotepadInteractor(notesRepository, markersRepository, noteMapper)
}
