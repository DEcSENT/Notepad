package com.dvinc.notepad.domain.usecase

import com.dvinc.notepad.domain.TestThreadScheduler
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.repository.note.NoteRepository
import com.dvinc.notepad.domain.usecase.notepad.NotepadUseCase
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito.verify

class NotepadUseCaseTest {

    private lateinit var notepadUseCase: NotepadUseCase

    private var note: Note = mock()

    private var noteList: List<Note> = listOf(note, note)

    private var noteRepository: NoteRepository = mock() {
        on { getNotes() }.doReturn(Flowable.just(noteList))
        on { deleteNoteById(anyLong()) }.doReturn(Completable.complete())
    }

    private val testScheduler = TestThreadScheduler()

    @Before
    fun setUp() {
        notepadUseCase = NotepadUseCase(noteRepository, testScheduler)
    }

    @Test
    fun getNotes() {
        notepadUseCase.getNotes()
        verify(noteRepository, times(1)).getNotes()
    }

    @Test
    fun `check correct result from getNotes()`() {
        notepadUseCase.getNotes()
            .test()
            .assertNoErrors()
            .assertValue(noteList)
    }

    @Test
    fun deleteNote() {
        notepadUseCase.deleteNote(anyLong())
        verify(noteRepository, times(1)).deleteNoteById(anyLong())
    }

    @Test
    fun `check correct result from deleteNote()`() {
        notepadUseCase.deleteNote(anyLong())
            .test()
            .assertNoErrors()
            .assertComplete()
    }
}
