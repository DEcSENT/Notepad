/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.mapper

import com.dvinc.notepad.domain.model.marker.MarkerType
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.dvinc.notepad.presentation.model.NoteUi
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NotePresentationMapper @Inject constructor() {

    private val sdfToday = SimpleDateFormat("HH:mm", Locale.getDefault())

    private val sdfWeek = SimpleDateFormat("d MMM", Locale.getDefault())

    private val sdfMonth = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

    fun fromDomainToUi(notes: List<Note>): List<NoteUi> {
        return notes.map { fromDomainToUi(it) }
    }

    fun mapMarkers(markers: List<MarkerType>): List<MarkerTypeUi> {
        return markers.map { mapMarker(it) }
    }

    fun createNote(
        noteId: Long,
        name: String,
        content: String,
        markerTypeUi: MarkerTypeUi
    ): Note {
        return Note(
            id = noteId,
            name = name,
            content = content,
            updateTime = System.currentTimeMillis(),
            markerType = mapMarker(markerTypeUi)
        )
    }

    private fun fromDomainToUi(note: Note): NoteUi {
        return with(note) {
            NoteUi(
                id = id,
                name = name,
                content = content,
                updateTime = mapNoteTimeToString(updateTime),
                markerType = mapMarker(markerType)
            )
        }
    }

    private fun mapNoteTimeToString(time: Long): String {
        val calendar = Calendar.getInstance()
        val currentCalendar = Calendar.getInstance()
        currentCalendar.time = Date(time)

        return if (calendar.get(Calendar.DAY_OF_YEAR) == currentCalendar.get(Calendar.DAY_OF_YEAR)
            && calendar.get(Calendar.DAY_OF_WEEK) == currentCalendar.get(Calendar.DAY_OF_WEEK)
        ) {
            sdfToday.format(time)
        } else if (calendar.get(Calendar.DAY_OF_YEAR) != currentCalendar.get(Calendar.DAY_OF_YEAR)
            && calendar.get(Calendar.WEEK_OF_YEAR) == currentCalendar.get(Calendar.WEEK_OF_YEAR)
        ) {
            sdfWeek.format(time)
        } else {
            sdfMonth.format(time)
        }
    }

    private fun mapMarker(marker: MarkerType): MarkerTypeUi {
        return when (marker) {
            MarkerType.NOTE -> MarkerTypeUi.NOTE
            MarkerType.CRITICAL -> MarkerTypeUi.CRITICAL
            MarkerType.TODO -> MarkerTypeUi.TODO
            MarkerType.IDEA -> MarkerTypeUi.IDEA
        }
    }

    private fun mapMarker(marker: MarkerTypeUi): MarkerType {
        return when (marker) {
            MarkerTypeUi.NOTE -> MarkerType.NOTE
            MarkerTypeUi.CRITICAL -> MarkerType.CRITICAL
            MarkerTypeUi.TODO -> MarkerType.TODO
            MarkerTypeUi.IDEA -> MarkerType.IDEA
        }
    }
}
