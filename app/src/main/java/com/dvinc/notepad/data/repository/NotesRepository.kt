/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.repository

import com.dvinc.notepad.data.database.NotepadDatabase
import com.dvinc.notepad.data.database.entity.NoteEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
        private val database: NotepadDatabase) {

    fun getNotes(): Flowable<List<NoteEntity>> {
        return database.notesDao().getNotes()
    }

    fun addNote(note: NoteEntity): Completable {
        return Completable.fromAction { database.notesDao().addNote(note) }
    }

    fun deleteNote(noteId: Int): Completable {
        return Completable.fromAction { database.notesDao().deleteNote(noteId) }
    }

    fun getNoteById(noteId: Int): Single<NoteEntity> {
        return Single.fromCallable { database.notesDao().getNoteById(noteId) }
    }
}
