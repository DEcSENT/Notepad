/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/14/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.data.repositories

import com.dvinc.notepad.domain.model.NoteMarker
import io.reactivex.Single

class MarkersRepository {

    fun getMarkers(): Single<List<NoteMarker>> {
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
 