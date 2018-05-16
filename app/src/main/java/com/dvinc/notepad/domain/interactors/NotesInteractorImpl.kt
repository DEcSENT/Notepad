/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.interactors

import com.dvinc.notepad.common.rxschedulers.RxSchedulers
import com.dvinc.notepad.data.repositories.MarkersRepository
import com.dvinc.notepad.data.repositories.NotesRepository
import com.dvinc.notepad.domain.mappers.NoteMapper
import com.dvinc.notepad.domain.model.Note
import com.dvinc.notepad.domain.model.NoteMarker
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class NotesInteractorImpl
@Inject constructor(
        private val notesRepository: NotesRepository,
        private val markersRepository: MarkersRepository,
        private val noteMapper: NoteMapper,
        private val rxSchedulers: RxSchedulers
) : NotesInteractor {

    override fun getNotes(): Flowable<List<Note>> {
        return notesRepository.getNotes()
                .compose(rxSchedulers.getIoToMainTransformerFlowable())
                .map { entities -> noteMapper.mapEntitiesToNotes(entities) }
    }

    override fun addNote(
            name: String,
            content: String,
            time: Long,
            markerColor: String,
            markerText: String
    ): Completable {
        return notesRepository.addNote(
                noteMapper.createEntity(name, content, time, markerColor, markerText))
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
        return notesRepository.updateNote(
                noteMapper.createEntity(name, content, time, markerColor, markerText, noteId))
                .compose(rxSchedulers.getIoToMainTransformerCompletable())
    }

    override fun deleteNote(noteId: Int): Completable {
        return notesRepository.deleteNoteById(noteId)
                .compose(rxSchedulers.getIoToMainTransformerCompletable())
    }

    override fun getNoteById(id: Long?): Single<Note> {
        /*
        * Well, this place contain some bad code, need to refactor it later.
        * The problem is in filtering id and null. Rx operator .filter can't handle this.
        */
        return if (id == null || id == 0L) {
            //Returning default empty note. Bad place here.
            Single.just(Note(0, "", "", "", "", ""))
        } else {
            notesRepository.getNoteById(id)
                    .compose(rxSchedulers.getIoToMainTransformerSingle())
                    .map { entity -> noteMapper.mapEntityToNote(entity) }
        }
    }

    override fun getNoteMarkers(): Single<List<NoteMarker>> {
        return markersRepository.getMarkers()
    }
}
