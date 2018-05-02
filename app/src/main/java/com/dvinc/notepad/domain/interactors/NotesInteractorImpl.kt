/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.interactors

import com.dvinc.notepad.data.database.entity.Note
import com.dvinc.notepad.data.repository.NotesRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

class NotesInteractorImpl
@Inject constructor(
        private val repository: NotesRepository
) : NotesInteractor {

    override fun getNotes(): Flowable<List<Note>> {
        return repository.getNotes()
    }

    override fun addNote(note: Note): Completable {
        return repository.addNote(note)
    }

    override fun deleteNote(noteId: Int): Completable {
        return repository.deleteNote(noteId)
    }

    override fun getNoteById(noteId: Int): Single<Note> {
        return repository.getNoteById(noteId)
    }
}
 