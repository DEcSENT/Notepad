/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.repository

import com.dvinc.notepad.data.database.NotepadDatabase
import com.dvinc.notepad.data.database.entity.NoteEntity
import com.dvinc.notepad.domain.model.NoteMarker
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
        private val database: NotepadDatabase) {

    fun getNotes(): Flowable<List<NoteEntity>> {
        return database.notesDao().getNotes()
    }

    fun addNote(note: NoteEntity): Completable {
        return Completable.fromAction { database.notesDao().addNote(note) }
    }

    fun deleteNote(noteId: Int): Completable {
        return Completable.fromAction { database.notesDao().deleteNote(noteId) }
    }

    fun getNoteById(noteId: Int): Single<NoteEntity> {
        return Single.fromCallable { database.notesDao().getNoteById(noteId) }
    }

    fun getNoteMarkers(): Single<List<NoteMarker>> {
        return Single.just(obtainMarkers())
    }

    private fun obtainMarkers(): List<NoteMarker> {
        val noteMarkers = ArrayList<NoteMarker>()
        val rawMarkers = mapOf(
                "Note" to "#000000",
                "Critical" to "#FFFF0000",
                "ToDo" to "#FF0000FF",
                "Idea" to "#FF00FF00")

        for (marker in rawMarkers) {
            noteMarkers.add(NoteMarker(marker.key, marker.value))
        }

        return noteMarkers
    }
}
