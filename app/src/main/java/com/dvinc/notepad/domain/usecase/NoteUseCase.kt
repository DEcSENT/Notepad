/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.usecase

import com.dvinc.notepad.common.extension.applyIoToMainSchedulers
import com.dvinc.notepad.data.database.entity.NoteEntity
import com.dvinc.notepad.domain.mapper.NoteMapper
import com.dvinc.notepad.domain.model.Note
import com.dvinc.notepad.domain.model.NoteMarker
import com.dvinc.notepad.domain.repository.MarkerRepository
import com.dvinc.notepad.domain.repository.NoteRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/*
 * This interactor is under construction.
 * The main idea is obtain all needed data from different sources and prepare it for presenter and view.
 */
class NoteUseCase
@Inject constructor(
        private val noteRepository: NoteRepository,
        private val markerRepository: MarkerRepository,
        private val noteMapper: NoteMapper
){

    fun getNoteById(id: Long): Single<Note> {
        return noteRepository.getNoteById(id)
                .zipWith(markerRepository.getMarkers(), BiFunction<NoteEntity, List<NoteMarker>, Note>
                { entity, markers ->
                    noteMapper.mapEntityToNote(entity, markers)
                })
                .applyIoToMainSchedulers()
    }

    fun getNoteMarkers(): Single<List<NoteMarker>> {
        return markerRepository.getMarkers()
    }

    fun addNoteInfo(
            noteId: Long?,
            name: String,
            content: String,
            time: Long,
            markerId: Int
    ): Completable {
        return if (noteId != null && noteId != 0L) {
            noteRepository.updateNote(
                    noteMapper.createEntity(name, content, time, markerId, noteId))
                    .applyIoToMainSchedulers()
        } else {
            noteRepository.addNote(
                    noteMapper.createEntity(name, content, time, markerId))
                    .applyIoToMainSchedulers()
        }
    }
}
