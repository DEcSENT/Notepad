/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.usecase.note

import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.repository.note.NoteRepository
import javax.inject.Inject

class NoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend fun getNoteById(id: Long): Note {
        return noteRepository.getNoteById(id)
    }

    suspend fun saveNote(note: Note) {
        noteRepository.addNote(note)
    }
}
