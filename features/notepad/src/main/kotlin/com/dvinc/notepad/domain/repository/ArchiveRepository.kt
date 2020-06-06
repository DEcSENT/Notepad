/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.domain.repository

import com.dvinc.notepad.domain.model.note.Note
import kotlinx.coroutines.flow.Flow

interface ArchiveRepository {

    suspend fun archiveNoteById(id: Long)

    fun getArchivedNotes(): Flow<List<Note>>
}
