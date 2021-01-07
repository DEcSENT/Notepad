/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.usecase.note

import com.dvinc.base.notepad.domain.model.Note
import com.dvinc.notepad.domain.repository.notepad.NotepadRepository
import javax.inject.Inject

class NoteUseCase @Inject constructor(
    private val notepadRepository: NotepadRepository
) {

    suspend fun getNoteById(id: Long): Note {
        return notepadRepository.getNoteById(id)
    }

    suspend fun saveNote(note: Note) {
        notepadRepository.addNote(note)
    }
}
