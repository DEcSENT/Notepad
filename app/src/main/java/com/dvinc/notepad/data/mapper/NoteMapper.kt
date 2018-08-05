/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.mapper

import com.dvinc.notepad.data.database.entity.MarkerTypeEntity
import com.dvinc.notepad.data.database.entity.NoteEntity
import com.dvinc.notepad.domain.model.MarkerType
import com.dvinc.notepad.domain.model.Note
import javax.inject.Inject

class NoteMapper @Inject constructor() {

    fun fromEntityToDomain(entities: List<NoteEntity>): List<Note> {
        return entities.map { fromEntityToDomain(it) }
    }

    fun fromEntityToDomain(note: NoteEntity): Note {
        return with(note) {
            Note(
                    id = id,
                    name = name,
                    content = content,
                    updateTime = updateTime,
                    markerType = when (markerType) {
                        MarkerTypeEntity.NOTE -> MarkerType.NOTE
                        MarkerTypeEntity.CRITICAL -> MarkerType.CRITICAL
                        MarkerTypeEntity.TODO -> MarkerType.TODO
                        MarkerTypeEntity.IDEA -> MarkerType.IDEA
                    }
            )
        }
    }
}
