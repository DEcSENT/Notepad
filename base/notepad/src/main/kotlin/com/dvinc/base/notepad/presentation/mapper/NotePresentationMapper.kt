/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.base.notepad.presentation.mapper

import com.dvinc.base.notepad.domain.model.Note
import com.dvinc.base.notepad.presentation.model.NoteUi
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class NotePresentationMapper @Inject constructor() {

    companion object {
        private const val DEFAULT_NOTE_ID = 0L
    }

    private val sdfToday = SimpleDateFormat("HH:mm", Locale.getDefault())

    private val sdfWeek = SimpleDateFormat("d MMM", Locale.getDefault())

    private val sdfMonth = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

    fun fromDomainToUi(notes: List<Note>): List<NoteUi> {
        return notes.map { fromDomainToUi(it) }
    }

    fun createNote(
        noteId: Long?,
        name: String,
        content: String
    ): Note {
        val id = noteId ?: DEFAULT_NOTE_ID
        return Note(
            id = id,
            name = name,
            content = content,
            updateTime = System.currentTimeMillis()
        )
    }

    private fun fromDomainToUi(note: Note): NoteUi {
        return with(note) {
            NoteUi(
                id = id,
                name = name,
                content = content,
                updateTime = mapNoteTimeToString(updateTime)
            )
        }
    }

    private fun mapNoteTimeToString(time: Long): String {
        val calendar = Calendar.getInstance()
        val currentCalendar = Calendar.getInstance()
        currentCalendar.time = Date(time)

        return if (calendar.get(Calendar.DAY_OF_YEAR) == currentCalendar.get(Calendar.DAY_OF_YEAR) &&
            calendar.get(Calendar.DAY_OF_WEEK) == currentCalendar.get(Calendar.DAY_OF_WEEK)
        ) {
            sdfToday.format(time)
        } else if (calendar.get(Calendar.DAY_OF_YEAR) != currentCalendar.get(Calendar.DAY_OF_YEAR) &&
            calendar.get(Calendar.WEEK_OF_YEAR) == currentCalendar.get(Calendar.WEEK_OF_YEAR)
        ) {
            sdfWeek.format(time)
        } else {
            sdfMonth.format(time)
        }
    }
}
