/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dvinc.notepad.data.database.converter.MarkerTypeConverter
import com.dvinc.notepad.data.database.entity.note.NoteEntity
import com.dvinc.notepad.data.database.dao.note.NoteDao

@Database(entities = arrayOf(NoteEntity::class), version = 1)
@TypeConverters(MarkerTypeConverter::class)
abstract class NotepadDatabase : RoomDatabase() {
    abstract fun notesDao(): NoteDao
}
