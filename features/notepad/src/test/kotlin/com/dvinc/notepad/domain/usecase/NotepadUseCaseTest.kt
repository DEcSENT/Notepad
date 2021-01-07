package com.dvinc.notepad.domain.usecase

import com.dvinc.notepad.BaseTest
import com.dvinc.notepad.domain.repository.notepad.NotepadRepository
import com.dvinc.notepad.domain.usecase.notepad.NotepadUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NotepadUseCaseTest : BaseTest() {

    private lateinit var notepadUseCase: NotepadUseCase

    private var notepadRepository: NotepadRepository = mock()

    @Before
    fun setUp() {
        notepadUseCase = NotepadUseCase(notepadRepository)
    }

    @Test
    fun `getNotes test`() {
        // Given

        // When
        notepadUseCase.getNotes()

        // Then
        verify(notepadRepository).getNotes()
    }

    @Test
    fun `deleteNote test`() = runBlocking {
        // Given
        val noteId = 10L

        // When
        notepadUseCase.deleteNote(noteId)

        // Then
        verify(notepadRepository).deleteNoteById(noteId)
    }
}
