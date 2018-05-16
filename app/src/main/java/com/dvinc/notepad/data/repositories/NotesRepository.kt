/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.repositories

import com.dvinc.notepad.data.database.NotepadDatabase
import com.dvinc.notepad.data.database.entity.NoteEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class NotesRepository
@Inject constructor(
        private val database: NotepadDatabase) {

    fun getNotes(): Flowable<List<NoteEntity>> {
        return database.notesDao().getNotes()
    }

    fun addNote(note: NoteEntity): Completable {
        return Completable.fromAction { database.notesDao().addNote(note) }
    }

    fun deleteNoteById(id: Int): Completable {
        return Completable.fromAction { database.notesDao().deleteNoteById(id) }
    }

    fun updateNote(note: NoteEntity): Completable {
        return Completable.fromAction { database.notesDao().updateNote(note) }
    }

    fun getNoteById(id: Long): Single<NoteEntity> {
        return Single.fromCallable { database.notesDao().getNoteById(id) }
    }
}
