package com.dvinc.notepad.data.repository.archive

import com.dvinc.core.database.dao.note.NoteDao
import com.dvinc.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.repository.ArchiveRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArchiveDataRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val noteMapper: NoteDataMapper
) : ArchiveRepository {

    override suspend fun archiveNoteById(id: Long) {
        noteDao.markNoteAsArchived(id)
    }

    override fun getArchivedNotes(): Flow<List<Note>> {
        return noteDao.getArchive()
            .map { noteMapper.fromEntityToDomain(it) }
    }
}
