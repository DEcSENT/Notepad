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
import com.dvinc.notepad.domain.repository.MarkerRepository
import com.dvinc.notepad.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun provideNotesInteractor(
            noteRepository: NoteRepository,
            markerRepository: MarkerRepository,
            noteMapper: NoteMapper
    ): NotesInteractor = NotesInteractor(noteRepository, markerRepository, noteMapper)

    @Provides
    fun provideNotepadInteractor(
            noteRepository: NoteRepository,
            markerRepository: MarkerRepository,
            noteMapper: NoteMapper
    ): NotepadInteractor = NotepadInteractor(noteRepository, markerRepository, noteMapper)
}
