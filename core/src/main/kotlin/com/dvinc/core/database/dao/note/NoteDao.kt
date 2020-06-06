/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.core.database.dao.note

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvinc.core.database.entity.note.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: NoteEntity)

    @Query("SELECT * FROM Notes WHERE is_archived = 0 ORDER BY id DESC")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("DELETE FROM Notes WHERE id = :id")
    suspend fun deleteNoteById(id: Long)

    @Query("SELECT * FROM Notes WHERE id = :id")
    suspend fun getNoteById(id: Long): NoteEntity

    @Query("UPDATE Notes SET is_archived = 1 WHERE id =:noteId")
    suspend fun markNoteAsArchived(noteId: Long)

    @Query("SELECT * FROM Notes WHERE is_archived = 1 ORDER BY id DESC")
    fun getArchive(): Flow<List<NoteEntity>>
}
