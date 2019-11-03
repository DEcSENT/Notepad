/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.usecase.note

import com.dvinc.notepad.common.execution.ThreadScheduler
import com.dvinc.notepad.common.execution.scheduleIoToUi
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.repository.note.NoteRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class NoteUseCase @Inject constructor(
        private val noteRepository: NoteRepository,
        private val scheduler: ThreadScheduler
) {

    fun getNoteById(id: Long): Single<Note> {
        return noteRepository.getNoteById(id)
                .scheduleIoToUi(scheduler)
    }

    fun saveNote(note: Note): Completable {
        return noteRepository.addNote(note)
                .scheduleIoToUi(scheduler)
    }
}
