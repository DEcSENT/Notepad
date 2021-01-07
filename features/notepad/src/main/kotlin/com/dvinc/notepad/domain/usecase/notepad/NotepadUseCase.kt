/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 6/8/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.usecase.notepad

import com.dvinc.base.notepad.domain.model.Note
import com.dvinc.notepad.domain.repository.notepad.NotepadRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotepadUseCase @Inject constructor(
    private val notepadRepository: NotepadRepository
) {

    fun getNotes(): Flow<List<Note>> {
        return notepadRepository.getNotes()
    }

    suspend fun deleteNote(noteId: Long) {
        notepadRepository.deleteNoteById(noteId)
    }

    suspend fun archiveNote(noteId: Long) {
        notepadRepository.archiveNoteById(noteId)
    }
}
