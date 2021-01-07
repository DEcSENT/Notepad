/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 6/8/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.repository.note

import com.dvinc.base.notepad.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun addNote(note: Note)

    suspend fun deleteNoteById(id: Long)

    suspend fun getNoteById(id: Long): Note
}
