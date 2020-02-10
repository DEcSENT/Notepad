/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dvinc.core.database.dao.note.NoteDao
import com.dvinc.core.database.entity.note.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotepadDatabase : RoomDatabase() {

    abstract fun notesDao(): NoteDao
}
