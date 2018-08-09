package com.dvinc.notepad.presentation.ui.notepad

import com.dvinc.notepad.domain.model.Note
import com.dvinc.notepad.domain.usecase.NotepadUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.model.NoteUi
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class NotepadPresenterTest {

    @Mock
    private lateinit var notepadUseCase: NotepadUseCase

    @Mock
    private lateinit var noteMapper: NotePresentationMapper

    @Mock
    private lateinit var note: Note

    @Mock
    private lateinit var noteUi: NoteUi

    @Mock
    private lateinit var view: NotepadView

    private lateinit var presenter: NotepadPresenter

    private lateinit var noteList: List<Note>

    private lateinit var noteUiList: List<NoteUi>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = NotepadPresenter(notepadUseCase, noteMapper)
        presenter.attachView(view)

        noteList = listOf(note, note)
        noteUiList = listOf(noteUi, noteUi)

        `when`(notepadUseCase.getNotes()).thenReturn(Flowable.just(noteList))
        `when`(noteMapper.fromDomainToUi(noteList)).thenReturn(noteUiList)
    }

    @Test
    fun `check view is attached`() {
        assert(presenter.view != null)
    }

    @Test
    fun `check view is detached`() {
        presenter.detachView()

        assert(presenter.view == null)
    }

    @Test
    fun initNotes() {
        presenter.initNotes()

        assert(presenter.view != null)
        verify(notepadUseCase, times(1)).getNotes()
        verify(noteMapper, times(1)).fromDomainToUi(noteList)
        verify(view, times(1)).showNotes(noteUiList)
        verify(view, times(0)).showError(anyString())
    }

    @Test
    fun `check empty list interactions`() {
        `when`(noteMapper.fromDomainToUi(noteList)).thenReturn(emptyList())

        presenter.initNotes()

        assert(presenter.view != null)
        verify(view, times(1)).setEmptyView(emptyList<NoteUi>().isEmpty())
        verify(view, times(1)).showNotes(emptyList())
        verify(view, times(0)).showError(anyString())
    }

    @Test
    fun `check error interactions`() {
        `when`(noteMapper.fromDomainToUi(noteList)).thenThrow(IllegalArgumentException("What?!"))

        presenter.initNotes()

        assert(presenter.view != null)
        verify(view, times(0)).showNotes(noteUiList)
        verify(view, times(0)).showMessage(anyString())
        //Replace this hardcode then message provider will be ready
        verify(view, times(1)).showError("What?!")
    }

    @Test
    fun deleteNote() {
        `when`(notepadUseCase.deleteNote(anyInt())).thenReturn(Completable.complete())

        presenter.deleteNote(anyInt())

        assert(presenter.view != null)
        //Replace this hardcode then message provider will be ready
        verify(view, times(1)).showMessage("Note successfully deleted")
        verify(view, times(0)).showError(anyString())
    }

    @Test
    fun `check error in deleteNote`() {
        `when`(notepadUseCase.deleteNote(anyInt())).thenReturn(Completable.error(IllegalArgumentException("What?!")))

        presenter.deleteNote(anyInt())

        assert(presenter.view != null)
        verify(view, times(0)).showMessage(anyString())
        //Replace this hardcode then message provider will be ready
        verify(view, times(1)).showError("What?!")
    }

    @Test
    fun onNoteSwiped() {
        presenter.onNoteSwiped(0, 1)

        assert(presenter.view != null)
        verify(view, times(1)).showDeleteNoteDialog(0, 1)
    }
}