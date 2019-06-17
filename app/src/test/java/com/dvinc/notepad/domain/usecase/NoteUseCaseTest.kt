package com.dvinc.notepad.domain.usecase

import com.dvinc.notepad.domain.TestThreadScheduler
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.repository.note.NoteRepository
import com.dvinc.notepad.domain.usecase.note.NoteUseCase
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito.verify

class NoteUseCaseTest {

    private lateinit var noteUseCase: NoteUseCase

    private var note: Note = mock()

    private val testScheduler = TestThreadScheduler()

    private var noteRepository: NoteRepository = mock() {
        on { getNoteById(anyLong()) }.doReturn(Single.just(note))
        on { addNote(note) }.doReturn(Completable.complete())
    }

    @Before
    fun setUp() {
        noteUseCase = NoteUseCase(noteRepository, testScheduler)
    }

    @Test
    fun getNoteById() {
        noteUseCase.getNoteById(anyLong())
        verify(noteRepository, times(1)).getNoteById(anyLong())
    }

    @Test
    fun `check correct result from getNoteById()`() {
        noteUseCase.getNoteById(anyLong())
            .test()
            .assertNoErrors()
            .assertValue(note)
    }

    @Test
    fun addNote() {
        noteUseCase.saveNote(note)
        verify(noteRepository, times(1)).addNote(note)
    }

    @Test
    fun `check correct result from addNote()`() {
        noteUseCase.saveNote(note)
            .test()
            .assertNoErrors()
            .assertComplete()
    }
}
