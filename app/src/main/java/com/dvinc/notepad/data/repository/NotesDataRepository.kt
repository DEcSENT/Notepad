/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.repository

import com.dvinc.notepad.data.database.NotepadDatabase
import com.dvinc.notepad.data.database.entity.NoteEntity
import com.dvinc.notepad.domain.repositories.NotesRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class NotesDataRepository
@Inject constructor(
        private val database: NotepadDatabase
): NotesRepository {

    override fun getNotes(): Flowable<List<NoteEntity>> {
        return database.notesDao().getNotes()
    }

    override fun addNote(note: NoteEntity): Completable {
        return Completable.fromAction { database.notesDao().addNote(note) }
    }

    override fun deleteNoteById(id: Int): Completable {
        return Completable.fromAction { database.notesDao().deleteNoteById(id) }
    }

    override fun updateNote(note: NoteEntity): Completable {
        return Completable.fromAction { database.notesDao().updateNote(note) }
    }

    override fun getNoteById(id: Long): Single<NoteEntity> {
        return Single.fromCallable { database.notesDao().getNoteById(id) }
    }
}
