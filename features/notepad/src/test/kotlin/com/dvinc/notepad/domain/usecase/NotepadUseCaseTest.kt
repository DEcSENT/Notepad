package com.dvinc.notepad.domain.usecase

import com.dvinc.notepad.BaseTest
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.repository.ArchiveRepository
import com.dvinc.notepad.domain.repository.note.NoteRepository
import com.dvinc.notepad.domain.usecase.notepad.NotepadUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NotepadUseCaseTest : BaseTest() {

    private lateinit var notepadUseCase: NotepadUseCase

    private var noteRepository: NoteRepository = mock()

    private var acrhiveRepository: ArchiveRepository = mock()

    @Before
    fun setUp() {
        notepadUseCase = NotepadUseCase(noteRepository, acrhiveRepository)
    }

    @Test
    fun `getNotes test`() {
        // Given

        // When
        notepadUseCase.getNotes()

        // Then
        verify(noteRepository).getNotes()
    }

    @Test
    fun `deleteNote test`() = runBlocking {
        // Given
        val noteId = 10L

        // When
        notepadUseCase.deleteNote(noteId)

        // Then
        verify(noteRepository).deleteNoteById(noteId)
    }
}
