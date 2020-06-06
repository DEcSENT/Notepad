package com.dvinc.notepad.domain.repository

import com.dvinc.notepad.domain.model.note.Note

interface ArchiveRepository {

    suspend fun archiveNoteById(id: Long)

    suspend fun getArchivedNotes(): List<Note>
}
