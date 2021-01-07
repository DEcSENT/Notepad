package com.dvinc.notepad.domain.usecase

import com.dvinc.notepad.BaseTest
import com.dvinc.base.notepad.domain.model.Note
import com.dvinc.notepad.domain.repository.note.NoteRepository
import com.dvinc.notepad.domain.usecase.note.NoteUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NoteUseCaseTest : BaseTest() {

    private lateinit var noteUseCase: NoteUseCase

    private var note: Note = mock()

    private var noteRepository: NoteRepository = mock()

    @Before
    fun setUp() {
        noteUseCase = NoteUseCase(noteRepository)
    }

    @Test
    fun `getNoteById test`() = runBlocking {
        // Given
        val noteId = 10L
        whenever(noteRepository.getNoteById(noteId)).thenReturn(note)

        // When
        noteUseCase.getNoteById(noteId)

        // Then
        val resultNote = noteRepository.getNoteById(noteId)
        assertEquals(resultNote, note)
    }

    @Test
    fun `addNote test`() = runBlocking {
        // Given

        // When
        noteUseCase.saveNote(note)

        // Then
        verify(noteRepository).addNote(note)
    }
}
