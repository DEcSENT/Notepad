/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 6/8/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.interactors

import com.dvinc.notepad.common.rxschedulers.RxSchedulers
import com.dvinc.notepad.data.repositories.MarkersRepository
import com.dvinc.notepad.data.repositories.NotesRepository
import com.dvinc.notepad.domain.mappers.NoteMapper
import com.dvinc.notepad.domain.model.Note
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class NotepadInteractor @Inject constructor(
        private val notesRepository: NotesRepository,
        private val markersRepository: MarkersRepository,
        private val noteMapper: NoteMapper,
        private val rxSchedulers: RxSchedulers
) {

    fun getNotes(): Flowable<List<Note>> {
        return notesRepository.getNotes()
                .map { entities ->
                    val markers = markersRepository.obtainMarkers()
                    noteMapper.mapEntitiesToNotes(entities, markers)
                }
                .compose(rxSchedulers.getIoToMainTransformerFlowable())
    }

    fun deleteNote(noteId: Int): Completable {
        return notesRepository.deleteNoteById(noteId)
                .compose(rxSchedulers.getIoToMainTransformerCompletable())
    }
}
