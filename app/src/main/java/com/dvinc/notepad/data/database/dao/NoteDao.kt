/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.dvinc.notepad.data.database.entity.Note
import io.reactivex.Flowable

@Dao
interface NoteDao {

    @Insert
    fun addNote(note: Note)

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    fun getNotes(): Flowable<List<Note>>

    @Query("DELETE FROM Notes WHERE id = :noteId")
    fun deleteNote(noteId: Int)

    @Query("SELECT * FROM Notes WHERE id = :noteId")
    fun getNoteById(noteId: Int): Note
}
