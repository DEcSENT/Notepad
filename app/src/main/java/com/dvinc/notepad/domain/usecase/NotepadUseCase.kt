/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 6/8/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.usecase

import com.dvinc.notepad.common.extension.applyIoToMainSchedulers
import com.dvinc.notepad.data.database.entity.NoteEntity
import com.dvinc.notepad.domain.mapper.NoteMapper
import com.dvinc.notepad.domain.model.Note
import com.dvinc.notepad.domain.model.NoteMarker
import com.dvinc.notepad.domain.repository.MarkerRepository
import com.dvinc.notepad.domain.repository.NoteRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class NotepadUseCase @Inject constructor(
        private val noteRepository: NoteRepository,
        private val markerRepository: MarkerRepository,
        private val noteMapper: NoteMapper
) {

    fun getNotes(): Flowable<List<Note>> {
        return noteRepository.getNotes()
                .zipWith(markerRepository.getMarkers().toFlowable(), BiFunction<List<NoteEntity>, List<NoteMarker>, List<Note>>
                { entity, markers ->
                    noteMapper.mapEntitiesToNotes(entity, markers)
                })
                .applyIoToMainSchedulers()
    }

    fun deleteNote(noteId: Int): Completable {
        return noteRepository.deleteNoteById(noteId)
                .applyIoToMainSchedulers()
    }
}
