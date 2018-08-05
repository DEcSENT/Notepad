/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/14/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.data.repository

import com.dvinc.notepad.domain.model.NoteMarker
import com.dvinc.notepad.domain.repositories.MarkersRepository
import io.reactivex.Single

class MarkersDataRepository : MarkersRepository {

    override fun getMarkers(): Single<List<NoteMarker>> {
        return Single.just(obtainMarkers())
    }

    private fun obtainMarkers(): List<NoteMarker> {
        val noteMarkers = ArrayList<NoteMarker>()
        val rawMarkers = mapOf(
                "Note" to "#000000",
                "Critical" to "#FFFF0000",
                "ToDo" to "#FF0000FF",
                "Idea" to "#FF00FF00")
        var index = 0
        for (marker in rawMarkers) {
            noteMarkers.add(NoteMarker(index++, marker.key, marker.value))
        }

        return noteMarkers
    }
}
