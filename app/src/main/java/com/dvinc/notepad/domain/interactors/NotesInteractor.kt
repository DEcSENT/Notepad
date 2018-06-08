/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.interactors

import com.dvinc.notepad.common.rxschedulers.RxSchedulers
import com.dvinc.notepad.domain.mappers.NoteMapper
import com.dvinc.notepad.domain.model.Note
import com.dvinc.notepad.domain.model.NoteMarker
import com.dvinc.notepad.domain.repositories.MarkersRepository
import com.dvinc.notepad.domain.repositories.NotesRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/*
 * This interactor is under construction.
 * The main idea is obtain all needed data from different sources and prepare it for presenter and view.
 */
class NotesInteractor
@Inject constructor(
        private val notesRepository: NotesRepository,
        private val markersRepository: MarkersRepository,
        private val noteMapper: NoteMapper,
        private val rxSchedulers: RxSchedulers
){

    fun getNoteById(id: Long): Single<Note> {
        return notesRepository.getNoteById(id)
                .compose(rxSchedulers.getIoToMainTransformerSingle())
                .map { entity ->
                    val markers = markersRepository.obtainMarkers()
                    noteMapper.mapEntityToNote(entity, markers)
                }
    }

    fun getNoteMarkers(): Single<List<NoteMarker>> {
        return markersRepository.getMarkers()
    }

    fun addNoteInfo(
            noteId: Long?,
            name: String,
            content: String,
            time: Long,
            markerId: Int
    ): Completable {
        return if (noteId != null && noteId != 0L) {
            notesRepository.updateNote(
                    noteMapper.createEntity(name, content, time, markerId, noteId))
                    .compose(rxSchedulers.getIoToMainTransformerCompletable())
        } else {
            notesRepository.addNote(
                    noteMapper.createEntity(name, content, time, markerId))
                    .compose(rxSchedulers.getIoToMainTransformerCompletable())
        }
    }
}
