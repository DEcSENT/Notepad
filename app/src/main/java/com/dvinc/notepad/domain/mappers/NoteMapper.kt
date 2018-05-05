/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.mappers

import com.dvinc.notepad.data.database.entity.NoteEntity
import com.dvinc.notepad.domain.model.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteMapper {

    private val sdfToday = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val sdfWeek = SimpleDateFormat("d MMM", Locale.getDefault())
    private val sdfMonth = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

    fun mapNotes(notesEntities: List<NoteEntity>) = notesEntities.map { note -> mapNote(note) }

    fun mapNote(entity: NoteEntity): Note {
        return Note(
                entity.id,
                entity.name,
                entity.content,
                mapNoteTimeToString(entity.updateTime),
                entity.markerColor,
                entity.markerText)
    }

    private fun mapNoteTimeToString(time: Long): String {
        val calendar = Calendar.getInstance()
        val currentCalendar = Calendar.getInstance()
        currentCalendar.time = Date(time)

        return if (calendar.get(Calendar.DAY_OF_YEAR) == currentCalendar.get(Calendar.DAY_OF_YEAR)
                && calendar.get(Calendar.DAY_OF_WEEK) == currentCalendar.get(Calendar.DAY_OF_WEEK)) {
            sdfToday.format(time)
        } else if (calendar.get(Calendar.DAY_OF_YEAR) == currentCalendar.get(Calendar.DAY_OF_YEAR)
                && calendar.get(Calendar.WEEK_OF_YEAR) == currentCalendar.get(Calendar.WEEK_OF_YEAR)) {
            sdfWeek.format(time)
        } else {
            sdfMonth.format(time)
        }
    }
}
