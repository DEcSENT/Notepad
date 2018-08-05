/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.dvinc.notepad.data.database.converter.MarkerTypeConverter
import com.dvinc.notepad.data.database.entity.NoteEntity
import com.dvinc.notepad.data.database.dao.NoteDao

@Database(entities = arrayOf(NoteEntity::class), version = 1)
@TypeConverters(MarkerTypeConverter::class)
abstract class NotepadDatabase : RoomDatabase() {
    abstract fun notesDao(): NoteDao
}
