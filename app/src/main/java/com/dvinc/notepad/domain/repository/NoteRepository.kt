/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 6/8/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.repository

import com.dvinc.notepad.data.database.entity.NoteEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface NoteRepository {

    fun getNotes(): Flowable<List<NoteEntity>>

    fun addNote(note: NoteEntity): Completable

    fun deleteNoteById(id: Int): Completable

    fun updateNote(note: NoteEntity): Completable

    fun getNoteById(id: Long): Single<NoteEntity>
}
