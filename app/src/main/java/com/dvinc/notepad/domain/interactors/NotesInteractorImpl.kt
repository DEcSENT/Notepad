/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.interactors

import com.dvinc.notepad.common.rxschedulers.RxSchedulers
import com.dvinc.notepad.data.database.entity.Note
import com.dvinc.notepad.data.repository.NotesRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class NotesInteractorImpl
@Inject constructor(
        private val repository: NotesRepository,
        private val rxSchedulers: RxSchedulers
) : NotesInteractor {

    override fun getNotes(): Flowable<List<Note>> {
        return repository.getNotes()
                .compose(rxSchedulers.getIoToMainTransformerFlowable())
    }

    override fun addNote(note: Note): Completable {
        return repository.addNote(note)
                .compose(rxSchedulers.getIoToMainTransformerCompletable())
    }

    override fun deleteNote(noteId: Int): Completable {
        return repository.deleteNote(noteId)
                .compose(rxSchedulers.getIoToMainTransformerCompletable())

    }

    override fun getNoteById(noteId: Int): Single<Note> {
        return repository.getNoteById(noteId)
                .compose(rxSchedulers.getIoToMainTransformerSingle())
    }
}
 