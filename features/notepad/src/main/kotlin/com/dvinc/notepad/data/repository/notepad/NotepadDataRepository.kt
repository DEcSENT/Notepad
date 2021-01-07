/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.repository.notepad

import com.dvinc.core.database.dao.note.NoteDao
import com.dvinc.base.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.base.notepad.domain.model.Note
import com.dvinc.core.database.dao.archive.ArchiveDao
import com.dvinc.notepad.domain.repository.notepad.NotepadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotepadDataRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val archiveDao: ArchiveDao,
    private val noteMapper: NoteDataMapper
) : NotepadRepository {

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
            .map { noteMapper.fromEntityToDomain(it) }
    }

    override suspend fun addNote(note: Note) {
        val noteEntity = noteMapper.fromDomainToEntity(note)
        noteDao.addNote(noteEntity)
    }

    override suspend fun deleteNoteById(id: Long) {
        noteDao.deleteNoteById(id)
    }

    override suspend fun getNoteById(id: Long): Note {
        val noteEntity = noteDao.getNoteById(id)
        return noteMapper.fromEntityToDomain(noteEntity)
    }

    override suspend fun archiveNoteById(id: Long) {
        archiveDao.markNoteAsArchived(id)
    }
}
