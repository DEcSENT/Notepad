package com.dvinc.notepad.data.repository.archive

import com.dvinc.core.database.dao.note.NoteDao
import com.dvinc.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.repository.ArchiveRepository
import javax.inject.Inject

class ArchiveDataRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val noteMapper: NoteDataMapper
) : ArchiveRepository {

    override suspend fun archiveNoteById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getArchivedNotes(): List<Note> {
        TODO("Not yet implemented")
    }
}
