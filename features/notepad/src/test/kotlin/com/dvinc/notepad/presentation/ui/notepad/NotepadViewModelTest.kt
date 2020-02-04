/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.notepad

import androidx.lifecycle.Observer
import com.dvinc.core.ui.ShowErrorMessage
import com.dvinc.core.ui.ShowMessage
import com.dvinc.core.ui.ViewCommand
import com.dvinc.notepad.CoroutinesTest
import com.dvinc.notepad.R
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.usecase.notepad.NotepadUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.model.NoteUi
import com.dvinc.notepad.presentation.ui.ViewCommandUtil
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import java.util.LinkedList

class NotepadViewModelTest : CoroutinesTest() {

    private lateinit var notepadViewModel: NotepadViewModel

    private var noteMapper: NotePresentationMapper = mock {
        on { fromDomainToUi(emptyList()) } doReturn emptyList()
    }

    private var testViewStateObserver: Observer<NotepadViewState> = mock()

    private var testViewCommandObserver: Observer<LinkedList<ViewCommand>> = mock()

    private var notepadUseCase: NotepadUseCase = mock {
        on { getNotes() } doReturn flow { emit(emptyList()) }
    }

    @Before
    fun setUp() {
        notepadViewModel = NotepadViewModel(notepadUseCase, noteMapper)
    }

    @Test
    fun `verify that Content state has empty list when empty notes list returned from repository`() = runCoroutineTest {
        // Given
        whenever(notepadUseCase.getNotes()).thenReturn(flow { emit(emptyList()) })
        val notepadViewModel = NotepadViewModel(notepadUseCase, noteMapper)
        notepadViewModel.viewState.observeForever(testViewStateObserver)

        // When

        // Then
        verify(testViewStateObserver).onChanged(NotepadViewState(emptyList(), true))
    }

    @Test
    fun `verify that state has Content when notes list returned from repository`() = runCoroutineTest {
        // Given
        whenever(notepadUseCase.getNotes()).thenReturn(flow { emit(getNotesList()) })
        whenever(noteMapper.fromDomainToUi(getNotesList())).thenReturn(getNotesUiList())
        val notepadViewModel = NotepadViewModel(notepadUseCase, noteMapper)
        notepadViewModel.viewState.observeForever(testViewStateObserver)

        // When

        // Then
        verify(testViewStateObserver).onChanged(NotepadViewState(getNotesUiList(), false))
    }

    @Test
    fun `when click on note then go to Note screen by view command`() {
        // Given
        val noteUi = NoteUi(100L, "test", "content", "21.12")

        // When
        notepadViewModel.onNoteItemClick(noteUi.id)

        // Then
        val expectedListWithSingleCommand = ViewCommandUtil.createViewCommandList(
            OpenNoteScreen(noteUi.id)
        )

        assertThat(notepadViewModel.viewCommands.value!!, `is`(expectedListWithSingleCommand))
    }

    @Test
    fun `show successful message after note deleting`() = runCoroutineTest {
        // Given
        val notepadViewModel = NotepadViewModel(notepadUseCase, noteMapper)
        notepadViewModel.viewCommands.observeForever(testViewCommandObserver)

        // When
        notepadViewModel.onNoteDelete(10L)

        // Then
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ShowMessage(R.string.note_successfully_deleted)
        )

        verify(testViewCommandObserver).onChanged(expectedViewCommandList)
    }

    @Test
    fun `show error message when an error occurred while note deleting`() = runCoroutineTest {
        // Given
        val noteUi = NoteUi(100L, "test", "content", "21.12")
        whenever(notepadUseCase.deleteNote(noteUi.id)).thenThrow(IllegalStateException())
        notepadViewModel.viewCommands.observeForever(testViewCommandObserver)

        // When
        notepadViewModel.onNoteDelete(noteUi.id)

        // Then
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ShowErrorMessage(R.string.error_while_deleting_note)
        )

        verify(testViewCommandObserver).onChanged(expectedViewCommandList)
    }

    @Test
    fun `show error message when an error occurred while notes loading`() = runCoroutineTest {
        // Given
        whenever(notepadUseCase.getNotes()).thenReturn(flow { throw NullPointerException() })
        notepadViewModel = NotepadViewModel(notepadUseCase, noteMapper)
        notepadViewModel.viewCommands.observeForever(testViewCommandObserver)

        // When

        // Then
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ShowErrorMessage(R.string.error_while_load_data_from_db)
        )

        verify(testViewCommandObserver).onChanged(expectedViewCommandList)
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
