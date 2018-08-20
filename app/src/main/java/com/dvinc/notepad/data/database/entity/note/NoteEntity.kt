/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.database.entity.note

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.dvinc.notepad.data.database.entity.marker.MarkerTypeEntity

@Entity(tableName = "Notes")
data class NoteEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Long,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "content")
        val content: String,
        @ColumnInfo(name = "update_time")
        val updateTime: Long,
        @ColumnInfo(name = "marker_type")
        val markerType: MarkerTypeEntity)