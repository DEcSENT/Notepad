/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 6/8/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.usecase.notepad

import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.repository.ArchiveRepository
import com.dvinc.notepad.domain.repository.note.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotepadUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val archiveRepository: ArchiveRepository
) {

    fun getNotes(): Flow<List<Note>> {
        return noteRepository.getNotes()
    }

    suspend fun deleteNote(noteId: Long) {
        noteRepository.deleteNoteById(noteId)
    }

    suspend fun archiveNote(noteId: Long) {
        archiveRepository.archiveNoteById(noteId)
    }
}
