package com.dvinc.notepad.domain.usecase

import com.dvinc.notepad.CoroutinesTest
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.repository.note.NoteRepository
import com.dvinc.notepad.domain.usecase.notepad.NotepadUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class NotepadUseCaseTest : CoroutinesTest() {

    private lateinit var notepadUseCase: NotepadUseCase

    private var note: Note = mock()

    private var noteRepository: NoteRepository = mock()

    @Before
    fun setUp() {
        notepadUseCase = NotepadUseCase(noteRepository)
    }

    @Test
    fun `getNotes test`() = runCoroutineTest{
        // Given

        // When
        notepadUseCase.getNotes()

        // Then
        verify(noteRepository).getNotes()
    }

    @Test
    fun `deleteNote test`() = runCoroutineTest{
        // Given
        val noteId = 10L

        // When
        notepadUseCase.deleteNote(noteId)

        // Then
        verify(noteRepository).deleteNoteById(noteId)
    }
}
