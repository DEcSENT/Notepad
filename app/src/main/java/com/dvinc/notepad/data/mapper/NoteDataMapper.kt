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
                    updateTime = updateTime,
                    markerType = mapMarkerType(markerType)
            )
        }
    }

    fun fromDomainToEntity(note: Note): NoteEntity {
        return with(note) {
            NoteEntity(
                    id = id,
                    name = name,
                    content = content,
                    updateTime = updateTime,
                    markerType = mapMarkerType(markerType)
            )
        }
    }

    fun mapMarkerType(entities: List<MarkerTypeEntity>): List<MarkerType> {
        return entities.map { mapMarkerType(it) }
    }

    private fun mapMarkerType(type: MarkerTypeEntity): MarkerType {
        return when (type) {
            MarkerTypeEntity.NOTE -> MarkerType.NOTE
            MarkerTypeEntity.CRITICAL -> MarkerType.CRITICAL
            MarkerTypeEntity.TODO -> MarkerType.TODO
            MarkerTypeEntity.IDEA -> MarkerType.IDEA
        }
    }

    private fun mapMarkerType(type: MarkerType): MarkerTypeEntity {
        return when (type) {
            MarkerType.NOTE -> MarkerTypeEntity.NOTE
            MarkerType.CRITICAL -> MarkerTypeEntity.CRITICAL
            MarkerType.TODO -> MarkerTypeEntity.TODO
            MarkerType.IDEA -> MarkerTypeEntity.IDEA
        }
    }
}
