/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.dvinc.notepad.data.database.dao.Note
import com.dvinc.notepad.data.database.entity.NoteDao

@Database(entities = arrayOf(Note::class), version = 1)
abstract class NotepadDatabase : RoomDatabase() {
    abstract fun notesDao(): NoteDao
}
