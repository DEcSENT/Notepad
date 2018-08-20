package com.dvinc.notepad.domain.usecase

import com.dvinc.notepad.domain.TestThreadScheduler
import com.dvinc.notepad.domain.model.Note
import com.dvinc.notepad.domain.repository.NoteRepository
import com.dvinc.notepad.domain.usecase.notepad.NotepadUseCase
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Test

import org.junit.Before
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class NotepadUseCaseTest {

    @Mock
    private lateinit var noteRepository: NoteRepository

    @Mock
    private lateinit var note: Note

    private lateinit var notepadUseCase: NotepadUseCase

    private lateinit var noteList: List<Note>

    private val testScheduler = TestThreadScheduler()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        notepadUseCase = NotepadUseCase(noteRepository, testScheduler)
        noteList = listOf(note, note)

        `when`(noteRepository.getNotes()).thenReturn(Flowable.just(noteList))
        `when`(noteRepository.deleteNoteById(anyInt())).thenReturn(Completable.complete())
    }

    @Test
    fun getNotes() {
        notepadUseCase.getNotes()
        verify(noteRepository).getNotes()
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
        notepadUseCase.deleteNote(anyInt())
        verify(noteRepository).deleteNoteById(anyInt())
    }

    @Test
    fun `check correct result from deleteNote()`() {
        notepadUseCase.deleteNote(anyInt())
                .test()
                .assertNoErrors()
                .assertComplete()
    }
}
