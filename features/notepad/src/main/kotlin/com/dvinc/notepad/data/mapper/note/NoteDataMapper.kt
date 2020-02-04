/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.mapper.note

import com.dvinc.core.database.entity.note.NoteEntity
import com.dvinc.notepad.domain.model.note.Note
import javax.inject.Inject

class NoteDataMapper @Inject constructor() {

    fun fromEntityToDomain(entities: List<NoteEntity>): List<Note> {
        return entities.map { fromEntityToDomain(it) }
    }

    fun fromEntityToDomain(note: NoteEntity): Note {
        return with(note) {
            Note(
                id = id,
                name = name,
                content = content,
                updateTime = updateTime
            )
        }
    }

    fun fromDomainToEntity(note: Note): NoteEntity {
        return with(note) {
            NoteEntity(
                id = id,
                name = name,
                content = content,
                updateTime = updateTime
            )
        }
    }
}
