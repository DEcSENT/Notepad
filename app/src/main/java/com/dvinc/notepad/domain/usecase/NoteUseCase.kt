/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.usecase

import com.dvinc.notepad.domain.common.execution.ThreadScheduler
import com.dvinc.notepad.domain.common.execution.scheduleIoToUi
import com.dvinc.notepad.domain.model.MarkerType
import com.dvinc.notepad.domain.model.Note
import com.dvinc.notepad.domain.repository.MarkerRepository
import com.dvinc.notepad.domain.repository.NoteRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class NoteUseCase @Inject constructor(
        private val noteRepository: NoteRepository,
        private val markerRepository: MarkerRepository,
        private val scheduler: ThreadScheduler
){

    fun getNoteById(id: Long): Single<Note> {
        return noteRepository.getNoteById(id)
                .scheduleIoToUi(scheduler)
    }

    fun getNoteMarkers(): Single<List<MarkerType>> {
        return markerRepository.getMarkers()
    }

    fun addNote(note: Note): Completable {
        return noteRepository.addNote(note)
                .scheduleIoToUi(scheduler)
    }

    fun updateNote(note: Note): Completable {
        return noteRepository.updateNote(note)
                .scheduleIoToUi(scheduler)
    }
}
