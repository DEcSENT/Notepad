/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.interactors

import com.dvinc.notepad.common.rxschedulers.RxSchedulers
import com.dvinc.notepad.data.database.entity.NoteEntity
import com.dvinc.notepad.data.repository.NotesRepository
import com.dvinc.notepad.domain.mappers.NoteMapper
import com.dvinc.notepad.domain.model.Note
import com.dvinc.notepad.domain.model.NoteMarker
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class NotesInteractorImpl
@Inject constructor(
        private val repository: NotesRepository,
        private val rxSchedulers: RxSchedulers
) : NotesInteractor {

    private val mapper: NoteMapper = NoteMapper()

    override fun getNotes(): Flowable<List<Note>> {
        return repository.getNotes()
                .compose(rxSchedulers.getIoToMainTransformerFlowable())
                .map { entities -> mapper.mapNotes(entities) }
    }

    override fun addNote(
            name: String,
            content: String,
            time: Long,
            markerColor: String,
            markerText: String
    ): Completable {
        return repository.addNote(NoteEntity(0, name, content, time, markerColor, markerText))
                .compose(rxSchedulers.getIoToMainTransformerCompletable())
    }

    override fun updateNote(
            noteId: Long,
            name: String,
            content: String,
            time: Long,
            markerColor: String,
            markerText: String
    ): Completable {
        return repository.updateNote(NoteEntity(noteId, name, content, time, markerColor, markerText))
                .compose(rxSchedulers.getIoToMainTransformerCompletable())
    }

    override fun deleteNote(noteId: Int): Completable {
        return repository.deleteNote(noteId)
                .compose(rxSchedulers.getIoToMainTransformerCompletable())
    }

    override fun getNoteById(noteId: Long?): Single<Note> {
        /*
        * Well, this place contain some bad code, need to refactor it later.
        * The problem is in filtering noteId and null. Rx operator .filter can't handle this.
        */
        return if (noteId == null) {
            //Returning default empty note. Bad place here.
            Single.just(Note(0, "", "", "", "", ""))
        } else {
            repository.getNoteById(noteId)
                    .compose(rxSchedulers.getIoToMainTransformerSingle())
                    .map { entity -> mapper.mapNote(entity) }
        }
    }

    override fun getMarkers(): Single<List<NoteMarker>> {
        return repository.getNoteMarkers()
    }
}
