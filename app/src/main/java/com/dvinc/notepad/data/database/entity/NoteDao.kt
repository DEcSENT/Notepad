/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.database.entity

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.dvinc.notepad.data.database.dao.Note

@Dao
interface NoteDao {

    @Insert
    fun insertNote(note: Note)

    @Query("SELECT * FROM Notes")
    fun getNotes(): List<Note>
}
