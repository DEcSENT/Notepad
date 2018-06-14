/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 6/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.data.repositories

import com.dvinc.notepad.data.database.NotepadDatabase
import com.dvinc.notepad.data.database.dao.NoteDao
import com.dvinc.notepad.data.database.entity.NoteEntity
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class NotesRepositoryTest {

    @Mock
    private lateinit var dataBase: NotepadDatabase

    @Mock
    private lateinit var repository: NotesDataRepository

    @Mock
    private lateinit var notesDao: NoteDao

    private val testNote = NoteEntity(0, "", "", 0L, 0)
    private val testId = 1

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = NotesDataRepository(dataBase)


        `when`(dataBase.notesDao()).thenReturn(notesDao)

        `when`(dataBase.notesDao().getNotes()).thenReturn(Flowable.just(listOf(testNote)))

        `when`(dataBase.notesDao().getNoteById(testId.toLong())).thenReturn(testNote)
    }

    @Test
    fun getNotesTest() {
        repository.getNotes()
        verify(dataBase.notesDao()).getNotes()
    }

    @Test
    fun addNoteTest() {
        repository.addNote(testNote).subscribe()
        verify(dataBase.notesDao()).addNote(testNote)
    }

    @Test
    fun deleteNoteTest() {
        repository.deleteNoteById(testId).subscribe()
        verify(dataBase.notesDao()).deleteNoteById(testId)
    }

    @Test
    fun updateNoteTest() {
        repository.updateNote(testNote).subscribe()
        verify(dataBase.notesDao()).updateNote(testNote)
    }

    @Test
    fun getNoteByIdTest() {
        repository.getNoteById(testId.toLong()).subscribe()
        verify(dataBase.notesDao()).getNoteById(testId.toLong())
    }
}
