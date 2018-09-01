/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.database.dao.note

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.dvinc.notepad.data.database.entity.note.NoteEntity
import io.reactivex.Flowable

@Dao
interface NoteDao {

    @Insert
    fun addNote(note: NoteEntity)

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    fun getNotes(): Flowable<List<NoteEntity>>

    @Query("DELETE FROM Notes WHERE id = :id")
    fun deleteNoteById(id: Int)

    @Query("SELECT * FROM Notes WHERE id = :id")
    fun getNoteById(id: Long): NoteEntity

    @Update
    fun updateNote(note: NoteEntity)
}
