/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 6/8/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.repository.note

import com.dvinc.notepad.domain.model.note.Note
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    fun addNote(note: Note): Completable

    suspend fun deleteNoteById(id: Long)

    fun getNoteById(id: Long): Single<Note>
}
