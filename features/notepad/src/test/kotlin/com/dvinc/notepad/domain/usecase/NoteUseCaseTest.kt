package com.dvinc.notepad.domain.usecase

import com.dvinc.notepad.CoroutinesTest
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.repository.note.NoteRepository
import com.dvinc.notepad.domain.usecase.note.NoteUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class NoteUseCaseTest : CoroutinesTest() {

    private lateinit var noteUseCase: NoteUseCase

    private var note: Note = mock()

    private var noteRepository: NoteRepository = mock()

    @Before
    fun setUp() {
        noteUseCase = NoteUseCase(noteRepository)
    }

    @Test
    fun `getNoteById test`() = runCoroutineTest {
        // Given
        val noteId = 10L

        // When
        noteUseCase.getNoteById(noteId)

        // Then
        verify(noteRepository).getNoteById(noteId)
    }

    @Test
    fun `addNote test`() = runCoroutineTest {
        // Given

        // When
        noteUseCase.saveNote(note)

        // Then
        verify(noteRepository).addNote(note)
    }
}
