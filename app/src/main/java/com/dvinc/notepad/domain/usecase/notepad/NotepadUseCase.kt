/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 6/8/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.usecase.notepad

import com.dvinc.notepad.domain.common.execution.ThreadScheduler
import com.dvinc.notepad.domain.common.execution.scheduleIoToUi
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.repository.note.NoteRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class NotepadUseCase @Inject constructor(
        private val noteRepository: NoteRepository,
        private val scheduler: ThreadScheduler
) {

    fun getNotes(): Flowable<List<Note>> {
        return noteRepository.getNotes()
                .scheduleIoToUi(scheduler)
    }

    fun deleteNote(noteId: Long): Completable {
        return noteRepository.deleteNoteById(noteId)
                .scheduleIoToUi(scheduler)
    }
}
