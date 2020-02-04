/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.note

import androidx.lifecycle.Observer
import com.dvinc.core.ui.ShowErrorMessage
import com.dvinc.core.ui.ViewCommand
import com.dvinc.notepad.CoroutinesTest
import com.dvinc.notepad.R
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.usecase.note.NoteUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.ui.ViewCommandUtil
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import java.util.LinkedList

class NoteViewModelTest : CoroutinesTest() {

    private val testNoteId = 10L

    private val note: Note = mock()

    private var noteMapper: NotePresentationMapper = mock()

    private val noteUseCase: NoteUseCase = mock()

    private val testViewStateObserver: Observer<NoteViewState> = mock()

    private val testViewCommandObserver: Observer<LinkedList<ViewCommand>> = mock()

    @Test
    fun `verify that existing note state is set`() = runCoroutineTest {
        // Given
        whenever(noteUseCase.getNoteById(testNoteId)).thenReturn(note)
        val noteViewModel = NoteViewModel(testNoteId, noteUseCase, noteMapper)
        noteViewModel.viewState.observeForever(testViewStateObserver)

        // When

        // Then
        val expectedViewState = NoteViewState.ExistingNoteViewState(note)
        verify(testViewStateObserver, times(1)).onChanged(expectedViewState)
    }

    @Test
    fun `verify new note state is loaded when no note ID`() {
        // Given
        val noteId = null
        val noteViewModel = NoteViewModel(noteId, noteUseCase, noteMapper)
        noteViewModel.viewState.observeForever(testViewStateObserver)

        // When

        // Then
        val expectedViewState = NoteViewState.NewNoteViewState
        verify(testViewStateObserver, times(1)).onChanged(expectedViewState)
    }

    @Test
    fun `verify that error message shown when an error occurred while loading note`() = runCoroutineTest {
        // Given
        val noteId = 10L
        whenever(noteUseCase.getNoteById(noteId)).thenThrow(NullPointerException())
        val noteViewModel = NoteViewModel(noteId, noteUseCase, noteMapper)
        noteViewModel.viewCommands.observeForever(testViewCommandObserver)

        // When

        // Then
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ShowErrorMessage(R.string.error_while_loading_note)
        )

        verify(testViewCommandObserver).onChanged(expectedViewCommandList)
    }

    @Test
    fun `verify that screen will close by view command after note saving`() = runCoroutineTest {
        // Given
        val noteName = "Test title"
        val noteContent = "Content"
        whenever(noteMapper.createNote(testNoteId, noteName, noteContent)).thenReturn(note)
        whenever(noteUseCase.getNoteById(testNoteId)).thenReturn(note)
        val noteViewModel = NoteViewModel(testNoteId, noteUseCase, noteMapper)
        noteViewModel.viewCommands.observeForever(testViewCommandObserver)

        // When
        noteViewModel.onSaveButtonClick(noteName, noteContent)

        // Then
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            CloseNoteScreen
        )
        verify(testViewCommandObserver).onChanged(expectedViewCommandList)
    }

    @Test
    fun `verify that error message is shown when an error occurred while note saving`() = runCoroutineTest {
        // Given
        val noteName = "Test title"
        val noteContent = "Content"
        whenever(noteMapper.createNote(testNoteId, noteName, noteContent)).thenReturn(note)
        whenever(noteUseCase.getNoteById(testNoteId)).thenReturn(note)
        whenever(noteUseCase.saveNote(note)).thenThrow(NullPointerException())
        val noteViewModel = NoteViewModel(testNoteId, noteUseCase, noteMapper)
        noteViewModel.viewCommands.observeForever(testViewCommandObserver)

        // When
        noteViewModel.onSaveButtonClick(noteName, noteContent)

        // Then
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ShowErrorMessage(R.string.error_while_adding_note)
        )
        verify(testViewCommandObserver).onChanged(expectedViewCommandList)
    }
}
