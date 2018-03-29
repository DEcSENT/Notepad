/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.repository

import com.dvinc.notepad.data.database.NotepadDatabase
import com.dvinc.notepad.data.database.entity.Note
import com.dvinc.notepad.di.qualifiers.IoScheduler
import com.dvinc.notepad.di.qualifiers.UiScheduler
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
        private val database: NotepadDatabase,
        private @IoScheduler val ioScheduler: Scheduler,
        private @UiScheduler val uiScheduler: Scheduler) {

    fun getNotes(): Flowable<List<Note>> {
        return database.notesDao().getNotes()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
    }

    fun addNote(note: Note): Completable {
        return Completable.fromAction { database.notesDao().addNote(note) }
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
    }

    fun deleteNote(noteId: Int): Completable {
        return Completable.fromAction { database.notesDao().deleteNote(noteId) }
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
    }

    fun getNoteById(noteId: Int): Single<Note> {
        return Single.fromCallable { database.notesDao().getNoteById(noteId) }
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
    }
}
