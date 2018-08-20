/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 6/8/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.repository.note

import com.dvinc.notepad.domain.model.note.Note
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface NoteRepository {

    fun getNotes(): Flowable<List<Note>>

    fun addNote(note: Note): Completable

    fun deleteNoteById(id: Int): Completable

    fun updateNote(note: Note): Completable

    fun getNoteById(id: Long): Single<Note>
}
