/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.notepad

import com.dvinc.core.extension.getOrAwaitValue
import com.dvinc.core.ui.NavigateTo
import com.dvinc.core.ui.ShowErrorMessage
import com.dvinc.core.ui.ShowMessage
import com.dvinc.notepad.BaseTest
import com.dvinc.notepad.R
import com.dvinc.base.notepad.domain.model.Note
import com.dvinc.notepad.domain.usecase.notepad.NotepadUseCase
import com.dvinc.base.notepad.presentation.adapter.notepad.NotepadSwipeDirection
import com.dvinc.base.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.base.notepad.presentation.model.NoteUi
import com.dvinc.notepad.presentation.ui.ViewCommandUtil
import com.dvinc.notepad.ui.notepad.NotepadViewModel
import com.dvinc.notepad.ui.notepad.NotepadViewState
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NotepadViewModelTest : BaseTest() {

    private lateinit var notepadViewModel: NotepadViewModel

    private var noteMapper: NotePresentationMapper = mock {
        on { fromDomainToUi(emptyList()) } doReturn emptyList()
    }

    private var notepadUseCase: NotepadUseCase = mock {
        on { getNotes() } doReturn flow { emit(emptyList<Note>()) }
    }

    @Before
    fun setUp() {
        notepadViewModel = NotepadViewModel(notepadUseCase, noteMapper)
    }

    @Test
    fun `verify that Content state has empty list when empty notes list returned from repository`() = runBlocking {
        // Given
        whenever(notepadUseCase.getNotes()).thenReturn(flow { emit(emptyList<Note>()) })
        val notepadViewModel = NotepadViewModel(notepadUseCase, noteMapper)

        // When

        // Then
        val resultViewState = notepadViewModel.viewState.getOrAwaitValue()
        val expectedViewState = NotepadViewState(emptyList(), true)
        assertEquals(resultViewState, expectedViewState)
    }

    @Test
    fun `verify that state has Content when notes list returned from repository`() = runBlocking {
        // Given
        whenever(notepadUseCase.getNotes()).thenReturn(flow { emit(getNotesList()) })
        whenever(noteMapper.fromDomainToUi(getNotesList())).thenReturn(getNotesUiList())
        val notepadViewModel = NotepadViewModel(notepadUseCase, noteMapper)

        // When

        // Then
        val resultViewState = notepadViewModel.viewState.getOrAwaitValue()
        val expectedViewState = NotepadViewState(getNotesUiList(), false)
        assertEquals(resultViewState, expectedViewState)
    }

    @Test
    fun `when click on note then go to Note screen by view command`() {
        // Given
        val noteUi = NoteUi(100L, "test", "content", "21.12")

        // When
        notepadViewModel.onNoteItemClick(noteUi.id)

        // Then
        val resultViewCommandList = notepadViewModel.viewCommands.getOrAwaitValue()
        val direction = NotepadFragmentDirections.toNoteFragment(noteUi.id)
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            NavigateTo(direction)
        )
        assertEquals(resultViewCommandList, expectedViewCommandList)
    }

    @Test
    fun `show successful message after note deleting`() = runBlocking {
        // Given
        val notepadViewModel = NotepadViewModel(notepadUseCase, noteMapper)

        // When
        notepadViewModel.onNoteSwipe(10L, NotepadSwipeDirection.RIGHT)

        // Then
        val resultViewCommandList = notepadViewModel.viewCommands.getOrAwaitValue()
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ShowMessage(R.string.note_successfully_deleted)
        )
        assertEquals(resultViewCommandList, expectedViewCommandList)
    }

    @Test
    fun `show error message when an error occurred while note deleting`() = runBlocking {
        // Given
        val noteUi = NoteUi(100L, "test", "content", "21.12")
        whenever(notepadUseCase.deleteNote(noteUi.id)).thenThrow(IllegalStateException())

        // When
        notepadViewModel.onNoteSwipe(noteUi.id, NotepadSwipeDirection.RIGHT)

        // Then
        val resultViewCommandList = notepadViewModel.viewCommands.getOrAwaitValue()
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ShowErrorMessage(R.string.error_while_deleting_note)
        )
        assertEquals(resultViewCommandList, expectedViewCommandList)
    }

    @Test
    fun `show error message when an error occurred while notes loading`() = runBlocking {
        // Given
        whenever(notepadUseCase.getNotes()).thenReturn(flow { throw NullPointerException() })
        notepadViewModel = NotepadViewModel(notepadUseCase, noteMapper)

        // When

        // Then
        val resultViewCommandList = notepadViewModel.viewCommands.getOrAwaitValue()
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ShowErrorMessage(R.string.error_while_load_data_from_db)
        )
        assertEquals(resultViewCommandList, expectedViewCommandList)
    }

    private fun getNotesList(): List<Note> {
        val note = Note(100L, "test", "content", 100L)
        return listOf(note, note)
    }

    private fun getNotesUiList(): List<NoteUi> {
        val noteUi = NoteUi(100L, "test", "content", "21.12")
        return listOf(noteUi, noteUi)
    }
}
