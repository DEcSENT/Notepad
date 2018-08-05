/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.mappers

import com.dvinc.notepad.data.database.entity.NoteEntity
import com.dvinc.notepad.domain.model.Note
import com.dvinc.notepad.domain.model.NoteMarker
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NoteMapper @Inject constructor() {

    private val sdfToday = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val sdfWeek = SimpleDateFormat("d MMM", Locale.getDefault())
    private val sdfMonth = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

    fun mapEntitiesToNotes(entities: List<NoteEntity>, markers: List<NoteMarker>)
            = entities.map { entity -> mapEntityToNote(entity, markers) }

    fun mapEntityToNote(entity: NoteEntity, markers: List<NoteMarker>): Note {
        val marker = markers[entity.markerId]
        return Note(
                entity.id,
                entity.name,
                entity.content,
                mapNoteTimeToString(entity.updateTime),
                entity.markerId,
                marker.color,
                marker.name)
    }

    fun createEntity(name: String,
                     content: String,
                     time: Long,
                     markerId: Int,
                     id: Long = 0): NoteEntity {
        return NoteEntity(id, name, content, time, markerId)
    }

    private fun mapNoteTimeToString(time: Long): String {
        val calendar = Calendar.getInstance()
        val currentCalendar = Calendar.getInstance()
        currentCalendar.time = Date(time)

        return if (calendar.get(Calendar.DAY_OF_YEAR) == currentCalendar.get(Calendar.DAY_OF_YEAR)
                && calendar.get(Calendar.DAY_OF_WEEK) == currentCalendar.get(Calendar.DAY_OF_WEEK)) {
            sdfToday.format(time)
        } else if (calendar.get(Calendar.DAY_OF_YEAR) != currentCalendar.get(Calendar.DAY_OF_YEAR)
                && calendar.get(Calendar.WEEK_OF_YEAR) == currentCalendar.get(Calendar.WEEK_OF_YEAR)) {
            sdfWeek.format(time)
        } else {
            sdfMonth.format(time)
        }
    }
}
