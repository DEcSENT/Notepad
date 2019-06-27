/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.notepad

import androidx.lifecycle.Observer
import com.dvinc.notepad.R
import com.dvinc.notepad.domain.model.marker.MarkerType
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.usecase.notepad.NotepadUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.dvinc.notepad.presentation.model.NoteUi
import com.dvinc.notepad.presentation.ui.ViewCommandUtil
import com.dvinc.notepad.presentation.ui.ViewModelTest
import com.dvinc.notepad.presentation.ui.base.ViewCommand
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import java.util.*

class NotepadViewModelTest : ViewModelTest() {

    private lateinit var notepadViewModel: NotepadViewModel

    private var noteMapper: NotePresentationMapper = mock()

    private var testViewStateObserver: Observer<NotepadViewState> = mock()

    private var testViewCommandObserver: Observer<LinkedList<ViewCommand>> = mock()

    private var notepadUseCase: NotepadUseCase = mock() {
        on { getNotes() } doReturn Flowable.just(emptyList())
    }

    @Before
    fun setUp() {
        notepadViewModel = NotepadViewModel(notepadUseCase, noteMapper)
    }

    @Test
    fun `verify that Content state has empty list when empty notes list returned from repository`() {
        // Given
        notepadViewModel.screenState.observeForever(testViewStateObserver)

        // When

        // Then
        verify(testViewStateObserver, times(1)).onChanged(NotepadViewState.Content(emptyList()))
    }

    @Test
    fun `verify that state has Content when notes list returned from repository`() {
        // Given
        val note = Note(100L, "test", "content", 100L, MarkerType.CRITICAL)
        val notesList = listOf(note)
        val noteUi = NoteUi(100L, "test", "content", "21.12", MarkerTypeUi.CRITICAL)
        val noteUiList = listOf(noteUi)

        // When
        whenever(notepadUseCase.getNotes()).thenReturn(Flowable.just(notesList))
        whenever(noteMapper.fromDomainToUi(notesList)).thenReturn(noteUiList)
        notepadViewModel = NotepadViewModel(notepadUseCase, noteMapper)
        notepadViewModel.screenState.observeForever(testViewStateObserver)

        // Then
        verify(testViewStateObserver, times(1)).onChanged(NotepadViewState.Content(noteUiList))
    }

    @Test
    fun `when click on note then go to Note screen by view command`() {
        // Given
        val noteUi = NoteUi(100L, "test", "content", "21.12", MarkerTypeUi.CRITICAL)

        // When
        notepadViewModel.onNoteItemClick(noteUi)

        // Then
        val expectedListWithSingleCommand = ViewCommandUtil.createViewCommandList(
            ViewCommand.OpenNoteScreen(noteUi.id)
        )

        assertThat(notepadViewModel.viewCommands.value!!, `is`(expectedListWithSingleCommand))
    }

    @Test
    fun `show successful message after note deleting`() {
        // Given
        val noteUi = NoteUi(100L, "test", "content", "21.12", MarkerTypeUi.CRITICAL)
        notepadViewModel.viewCommands.observeForever(testViewCommandObserver)

        // When
        whenever(notepadUseCase.deleteNote(noteUi.id)).thenReturn(Completable.complete())
        notepadViewModel.onNoteDelete(noteUi)

        // Then
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ViewCommand.ShowMessage(R.string.note_successfully_deleted)
        )

        verify(testViewCommandObserver).onChanged(expectedViewCommandList)
    }

    @Test
    fun `show error message when an error occurred while note deleting`() {
        // Given
        val noteUi = NoteUi(100L, "test", "content", "21.12", MarkerTypeUi.CRITICAL)
        notepadViewModel.viewCommands.observeForever(testViewCommandObserver)

        // When
        whenever(notepadUseCase.deleteNote(noteUi.id)).thenReturn(Completable.error(IllegalStateException()))
        notepadViewModel.onNoteDelete(noteUi)

        // Then
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ViewCommand.ShowErrorMessage(R.string.error_while_deleting_note)
        )

        verify(testViewCommandObserver).onChanged(expectedViewCommandList)
    }

    @Test
    fun  `show error message when an error occurred while notes loading`() {
        // Given

        // When
        whenever(notepadUseCase.getNotes()).thenReturn(Flowable.error(NullPointerException()))
        notepadViewModel = NotepadViewModel(notepadUseCase, noteMapper)
        notepadViewModel.viewCommands.observeForever(testViewCommandObserver)

        // Then
        val expectedViewCommandList = ViewCommandUtil.createViewCommandList(
            ViewCommand.ShowErrorMessage(R.string.error_while_load_data_from_db)
        )

        verify(testViewCommandObserver).onChanged(expectedViewCommandList)
    }
}
