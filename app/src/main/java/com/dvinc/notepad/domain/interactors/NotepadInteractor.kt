/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 6/8/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.interactors

import com.dvinc.notepad.common.applyIoToMainSchedulers
import com.dvinc.notepad.domain.mappers.NoteMapper
import com.dvinc.notepad.domain.model.Note
import com.dvinc.notepad.domain.repositories.MarkersRepository
import com.dvinc.notepad.domain.repositories.NotesRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class NotepadInteractor @Inject constructor(
        private val notesRepository: NotesRepository,
        private val markersRepository: MarkersRepository,
        private val noteMapper: NoteMapper
) {

    fun getNotes(): Flowable<List<Note>> {
        return notesRepository.getNotes()
                .map { entities ->
                    val markers = markersRepository.obtainMarkers()
                    noteMapper.mapEntitiesToNotes(entities, markers)
                }
                .applyIoToMainSchedulers()
    }

    fun deleteNote(noteId: Int): Completable {
        return notesRepository.deleteNoteById(noteId)
                .applyIoToMainSchedulers()
    }
}
