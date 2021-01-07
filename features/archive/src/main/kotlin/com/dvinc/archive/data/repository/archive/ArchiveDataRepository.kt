/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.archive.data.repository.archive

import com.dvinc.archive.domain.repository.ArchiveRepository
import com.dvinc.base.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.base.notepad.domain.model.Note
import com.dvinc.core.database.dao.archive.ArchiveDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArchiveDataRepository @Inject constructor(
    private val archiveDao: ArchiveDao,
    private val noteMapper: NoteDataMapper
) : ArchiveRepository {

    override suspend fun archiveNoteById(id: Long) {
        archiveDao.markNoteAsArchived(id)
    }

    override fun getArchivedNotes(): Flow<List<Note>> {
        return archiveDao.getArchive()
            .map { noteMapper.fromEntityToDomain(it) }
    }
}
