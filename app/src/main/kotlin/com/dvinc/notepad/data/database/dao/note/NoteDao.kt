/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.database.dao.note

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvinc.notepad.data.database.entity.note.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: NoteEntity)

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("DELETE FROM Notes WHERE id = :id")
    suspend fun deleteNoteById(id: Long)

    @Query("SELECT * FROM Notes WHERE id = :id")
    fun getNoteById(id: Long): NoteEntity
}
