/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 6/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.data.repository

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
class NoteRepositoryTest {

    @Mock
    private lateinit var noteDao: NoteDao

    @Mock
    private lateinit var repository: NoteDataRepository

    @Mock
    private lateinit var notesDao: NoteDao

    private val testNote = NoteEntity(0, "", "", 0L, 0)
    private val testId = 1

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = NoteDataRepository(noteDao)


        `when`(noteDao.getNotes()).thenReturn(Flowable.just(listOf(testNote)))

        `when`(noteDao.getNoteById(testId.toLong())).thenReturn(testNote)
    }

    @Test
    fun getNotesTest() {
        repository.getNotes()
        verify(noteDao).getNotes()
    }

    @Test
    fun addNoteTest() {
        repository.addNote(testNote).subscribe()
        verify(noteDao).addNote(testNote)
    }

    @Test
    fun deleteNoteTest() {
        repository.deleteNoteById(testId).subscribe()
        verify(noteDao).deleteNoteById(testId)
    }

    @Test
    fun updateNoteTest() {
        repository.updateNote(testNote).subscribe()
        verify(noteDao).updateNote(testNote)
    }

    @Test
    fun getNoteByIdTest() {
        repository.getNoteById(testId.toLong()).subscribe()
        verify(noteDao).getNoteById(testId.toLong())
    }
}