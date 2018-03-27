/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.repository

import com.dvinc.notepad.data.database.dao.Note
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NotesRepository {

    fun getNotes(): Single<List<Note>> {
        return Single.just(createNotes())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun createNotes(): List<Note> {
        val notes = ArrayList<Note>()
        notes.add(Note(0, "Note 1", "Well, Kotlin isn't looks so good...", System.currentTimeMillis()))
        notes.add(Note(1, "Note 2", "But maybe sometimes i will learn it...", System.currentTimeMillis()))

        return notes
    }
}
