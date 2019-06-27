/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.note

import androidx.lifecycle.Observer
import com.dvinc.notepad.R
import com.dvinc.notepad.domain.model.marker.MarkerType
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.usecase.marker.MarkerUseCase
import com.dvinc.notepad.domain.usecase.note.NoteUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.dvinc.notepad.presentation.ui.ViewCommandUtil
import com.dvinc.notepad.presentation.ui.ViewModelTest
import com.dvinc.notepad.presentation.ui.base.ViewCommand
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import java.util.*

class NoteViewModelTest : ViewModelTest() {

    private lateinit var noteViewModel: NoteViewModel

    private val markerTypeList = MarkerType.values().toList()

    private val markerTypeUiList = MarkerTypeUi.values().toList()

    private val note: Note = mock()

    private var noteMapper: NotePresentationMapper = mock {
        on { mapMarkers(markerTypeList) } doReturn markerTypeUiList
    }

    private val noteUseCase: NoteUseCase = mock {
        on { getNoteById(anyLong()) } doReturn Single.just(note)
    }

    private val markerUseCase: MarkerUseCase = mock {
        on { getNoteMarkers() } doReturn Single.just(markerTypeList)
    }

    private val testViewStateObserver: Observer<NoteViewState> = mock()

    private val testViewCommandObserver: Observer<LinkedList<ViewCommand>> = mock()

    @Before
    fun setUp() {
        noteViewModel = NoteViewModel(noteUseCase, markerUseCase, noteMapper)
    }

    @Test
    fun `verify that existing note state is set`() {
        // Given
        noteViewModel.screenState.observeForever(testViewStateObserver)

        // When
        noteViewModel.initNote(10L)

        // Then
        val expectedViewState = NoteViewState.ExistingNoteViewState(note, markerTypeUiList)
        verify(testViewStateObserver, times(1)).onChanged(expectedViewState)
    }

    @Test
    fun `verify new note state is loaded when no note ID`() {
        // Given
        noteViewModel.screenState.observeForever(testViewStateObserver)

        // When
        noteViewModel.initNote(null)

        // Then
        val expectedViewState = NoteViewState.NewNoteViewState(markerTypeUiList)
        verify(testViewStateObserver, times(1)).onChanged(expectedViewState)
    }

    @Test
    fun `verify new note state is loaded when note ID has default value`() {
        // Given
        noteViewModel.screenState.observeForever(testViewStateObserver)

        // When
        noteViewModel.initNote(0L)

        // Then
        val expectedViewState = NoteViewState.NewNoteViewState(markerTypeUiList)
        verify(testViewStateObserver, times(1)).onChanged(expectedViewState)
    }

    @Test
    fun `verify that error message is shown when an error occurred while loading note`() {
        // Given
        val noteId = 10L
        noteViewModel.viewCommands.observeForever(testViewCommandObserver)

        // When
        whenever(noteUseCase.getNoteById(noteId)).thenReturn(Single.error(NullPointerException()))
        noteViewModel.initNote(noteId)

        // Then
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ViewCommand.ShowErrorMessage(R.string.error_while_loading_note)
        )

        verify(testViewCommandObserver).onChanged(expectedViewCommandList)
    }

    @Test
    fun `verify that error message is shown when an error occurred while loading note markers`() {
        // Given
        val noteId = 10L
        noteViewModel.viewCommands.observeForever(testViewCommandObserver)

        // When
        whenever(markerUseCase.getNoteMarkers()).thenReturn(Single.error(NullPointerException()))
        noteViewModel.initNote(noteId)

        // Then
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ViewCommand.ShowErrorMessage(R.string.error_while_loading_note)
        )

        verify(testViewCommandObserver).onChanged(expectedViewCommandList)
    }

    @Test
    fun `verify that screen will be close by view command after note saving`() {
        // Given
        val noteId = 0L
        val noteName = "Test title"
        val noteContent = "Content"
        val noteTypeUi = MarkerTypeUi.CRITICAL
        noteViewModel.viewCommands.observeForever(testViewCommandObserver)

        // When
        whenever(noteMapper.createNote(noteId, noteName, noteContent, noteTypeUi)).thenReturn(note)
        whenever(noteUseCase.saveNote(note)).thenReturn(Completable.complete())
        noteViewModel.onSaveButtonClick(noteName, noteContent, noteTypeUi)

        // Then
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ViewCommand.CloseNoteScreen
        )
        verify(testViewCommandObserver).onChanged(expectedViewCommandList)
    }

    @Test
    fun `verify that error message is shown when an error occurred while note saving`() {
        // Given
        val noteId = 0L
        val noteName = "Test title"
        val noteContent = "Content"
        val noteTypeUi = MarkerTypeUi.CRITICAL
        noteViewModel.viewCommands.observeForever(testViewCommandObserver)

        // When
        whenever(noteMapper.createNote(noteId, noteName, noteContent, noteTypeUi)).thenReturn(note)
        whenever(noteUseCase.saveNote(note)).thenReturn(Completable.error(NullPointerException()))
        noteViewModel.onSaveButtonClick(noteName, noteContent, noteTypeUi)

        // Then
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ViewCommand.ShowErrorMessage(R.string.error_while_adding_note)
        )
        verify(testViewCommandObserver).onChanged(expectedViewCommandList)
    }
}
