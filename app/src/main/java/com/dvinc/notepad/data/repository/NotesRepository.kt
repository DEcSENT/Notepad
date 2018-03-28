/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.repository

import com.dvinc.notepad.data.database.NotepadDatabase
import com.dvinc.notepad.data.database.entity.Note
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(private val database: NotepadDatabase) {

    fun getNotes(): Single<List<Note>> {
        return Single.fromCallable { database.notesDao().getNotes() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun addNote(note: Note): Completable {
        return Completable.fromAction { database.notesDao().addNote(note) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteNote(noteId: Int): Completable {
        return Completable.fromAction { database.notesDao().deleteNote(noteId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getNoteById(noteId: Int): Single<Note> {
        return Single.fromCallable { database.notesDao().getNoteById(noteId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
