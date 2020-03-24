/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.note

import com.dvinc.core.extension.getOrAwaitValue
import com.dvinc.core.ui.NavigateUp
import com.dvinc.core.ui.ShowErrorMessage
import com.dvinc.notepad.BaseTest
import com.dvinc.notepad.R
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.usecase.note.NoteUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.ui.ViewCommandUtil
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class NoteViewModelTest : BaseTest() {

    private val testNoteId = 10L

    private val note: Note = mock()

    private var noteMapper: NotePresentationMapper = mock()

    private val noteUseCase: NoteUseCase = mock()

    @Test
    fun `verify that existing note state is set`() = runBlocking {
        // Given
        whenever(noteUseCase.getNoteById(testNoteId)).thenReturn(note)
        val noteViewModel = NoteViewModel(testNoteId, noteUseCase, noteMapper)

        // When

        // Then
        val resultViewState = noteViewModel.viewState.getOrAwaitValue()
        val expectedViewState = NoteViewState.ExistingNoteViewState(note)
        assertEquals(resultViewState, expectedViewState)
    }

    @Test
    fun `verify new note state is loaded when no note ID`() {
        // Given
        val noteId = 0L
        val noteViewModel = NoteViewModel(noteId, noteUseCase, noteMapper)

        // When

        // Then
        val resultViewState = noteViewModel.viewState.getOrAwaitValue()
        val expectedViewState = NoteViewState.NewNoteViewState
        assertEquals(resultViewState, expectedViewState)
    }

    @Test
    fun `verify that error message shown when an error occurred while loading note`() = runBlocking {
        // Given
        val noteId = 10L
        whenever(noteUseCase.getNoteById(noteId)).thenThrow(NullPointerException())
        val noteViewModel = NoteViewModel(noteId, noteUseCase, noteMapper)

        // When

        // Then
        val resultViewCommandList = noteViewModel.viewCommands.getOrAwaitValue()
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ShowErrorMessage(R.string.error_while_loading_note)
        )
        assertEquals(resultViewCommandList, expectedViewCommandList)
    }

    @Test
    fun `verify that screen will close by view command after note saving`() = runBlocking {
        // Given
        val noteName = "Test title"
        val noteContent = "Content"
        whenever(noteMapper.createNote(testNoteId, noteName, noteContent)).thenReturn(note)
        whenever(noteUseCase.getNoteById(testNoteId)).thenReturn(note)
        val noteViewModel = NoteViewModel(testNoteId, noteUseCase, noteMapper)

        // When
        noteViewModel.onSaveButtonClick(noteName, noteContent)

        // Then
        val resultViewCommandList = noteViewModel.viewCommands.getOrAwaitValue()
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            NavigateUp
        )
        assertEquals(resultViewCommandList, expectedViewCommandList)
    }

    @Test
    fun `verify that error message is shown when an error occurred while note saving`() = runBlocking {
        // Given
        val noteName = "Test title"
        val noteContent = "Content"
        whenever(noteMapper.createNote(testNoteId, noteName, noteContent)).thenReturn(note)
        whenever(noteUseCase.getNoteById(testNoteId)).thenReturn(note)
        whenever(noteUseCase.saveNote(note)).thenThrow(NullPointerException())
        val noteViewModel = NoteViewModel(testNoteId, noteUseCase, noteMapper)

        // When
        noteViewModel.onSaveButtonClick(noteName, noteContent)

        // Then
        val resultViewCommandList = noteViewModel.viewCommands.getOrAwaitValue()
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ShowErrorMessage(R.string.error_while_adding_note)
        )
        assertEquals(resultViewCommandList, expectedViewCommandList)
    }
}
