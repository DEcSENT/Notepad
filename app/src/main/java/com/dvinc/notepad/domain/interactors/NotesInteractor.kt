/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.interactors

import com.dvinc.notepad.data.database.entity.Note
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface NotesInteractor {

    fun getNotes(): Flowable<List<Note>>

    fun addNote(note: Note): Completable

    fun deleteNote(noteId: Int): Completable

    fun getNoteById(noteId: Int): Single<Note>
}
 