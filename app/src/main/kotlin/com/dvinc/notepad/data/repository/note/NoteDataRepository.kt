/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.repository.note

import com.dvinc.notepad.data.database.dao.note.NoteDao
import com.dvinc.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.repository.note.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteDataRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val noteMapper: NoteDataMapper
) : NoteRepository {

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
}
