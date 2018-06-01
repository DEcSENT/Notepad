/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.interactors

import com.dvinc.notepad.domain.model.Note
import com.dvinc.notepad.domain.model.NoteMarker
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface NotesInteractor {

    fun getNotes(): Flowable<List<Note>>

    fun deleteNote(noteId: Int): Completable

    fun getNoteById(id: Long): Single<Note>

    fun getNoteMarkers(): Single<List<NoteMarker>>

    fun addNoteInfo(
            noteId: Long?,
            name: String,
            content: String,
            time: Long,
            markerId: Int
    ): Completable
}
