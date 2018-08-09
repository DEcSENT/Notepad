package com.dvinc.notepad.domain.usecase

import com.dvinc.notepad.domain.TestThreadScheduler
import com.dvinc.notepad.domain.model.MarkerType
import com.dvinc.notepad.domain.model.Note
import com.dvinc.notepad.domain.repository.MarkerRepository
import com.dvinc.notepad.domain.repository.NoteRepository
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test

import org.junit.Before
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class NoteUseCaseTest {

    @Mock
    private lateinit var noteRepository: NoteRepository

    @Mock
    private lateinit var markerRepository: MarkerRepository

    @Mock
    private lateinit var note: Note

    private lateinit var noteUseCase: NoteUseCase

    private val testScheduler = TestThreadScheduler()

    private val markerTypeList = MarkerType.values().toList()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        noteUseCase = NoteUseCase(noteRepository, markerRepository, testScheduler)

        `when`(noteRepository.getNoteById(anyLong())).thenReturn(Single.just(note))
        `when`(markerRepository.getMarkers()).thenReturn(Single.just(markerTypeList))
        `when`(noteRepository.addNote(note)).thenReturn(Completable.complete())
        `when`(noteRepository.updateNote(note)).thenReturn(Completable.complete())
    }

    @Test
    fun getNoteById() {
        noteUseCase.getNoteById(anyLong())
        verify(noteRepository).getNoteById(anyLong())
    }

    @Test
    fun `check correct result from getNoteById()`() {
        noteUseCase.getNoteById(anyLong())
                .test()
                .assertNoErrors()
                .assertValue(note)
    }

    @Test
    fun getNoteMarkers() {
        noteUseCase.getNoteMarkers()
        verify(markerRepository).getMarkers()
    }

    @Test
    fun `check correct result from getNoteMarkers()`() {
        noteUseCase.getNoteMarkers()
                .test()
                .assertNoErrors()
                .assertResult(markerTypeList)
    }

    @Test
    fun addNote() {
        noteUseCase.addNote(note)
        verify(noteRepository).addNote(note)
    }

    @Test
    fun `check correct result from addNote()`() {
        noteUseCase.addNote(note)
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun updateNote() {
        noteUseCase.updateNote(note)
        verify(noteRepository).updateNote(note)
    }

    @Test
    fun `check correct result from updateNote()`() {
        noteUseCase.updateNote(note)
                .test()
                .assertNoErrors()
                .assertComplete()
    }
}
