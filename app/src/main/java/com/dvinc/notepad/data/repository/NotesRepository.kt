/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.repository

import com.dvinc.notepad.data.database.NotepadDatabase
import com.dvinc.notepad.data.database.entity.Note
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(private val database: NotepadDatabase) {

    fun getNotes(): Single<List<Note>> {
        return Single.fromCallable{createNotes()}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun createNotes(): List<Note> {
        database.notesDao().insertNote(Note(0, "Note 1", "Well, Kotlin isn't looks so good...", System.currentTimeMillis()))
        database.notesDao().insertNote(Note(0, "Note 2", "But maybe sometimes i will learn it...", System.currentTimeMillis()))
        return database.notesDao().getNotes()
    }
}
