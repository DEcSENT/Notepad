package com.dvinc.notepad.presentation.ui.notepad

import androidx.lifecycle.MutableLiveData
import com.dvinc.notepad.R
import com.dvinc.notepad.domain.usecase.notepad.NotepadUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.dvinc.notepad.presentation.model.NoteUi
import com.dvinc.notepad.presentation.ui.base.BaseViewModel
import com.dvinc.notepad.presentation.ui.base.ViewCommand
import com.dvinc.notepad.presentation.ui.base.ViewCommand.OpenNoteScreen
import com.dvinc.notepad.presentation.ui.notepad.NotepadViewState.*
import timber.log.Timber
import javax.inject.Inject

class NotepadViewModel @Inject constructor(
    private val notepadUseCase: NotepadUseCase,
    private val noteMapper: NotePresentationMapper
) : BaseViewModel() {

    companion object {
        private const val TAG = "NotepadViewModel"
    }

    val screenState = MutableLiveData<NotepadViewState>()

    init {
        loadNotes()
    }

    fun onNoteItemClick(note: NoteUi) {
        val openNoteScreenCommand = OpenNoteScreen(noteId = note.id)
        viewCommands.onNext(openNoteScreenCommand)
    }

    fun onNoteDelete(note: NoteUi) {
        notepadUseCase.deleteNote(note.id)
            .subscribe(
                {
                    showMessage(R.string.note_successfully_deleted)
                },
                {
                    Timber.tag(TAG).e(it)
                    showErrorMessage(R.string.error_while_deleting_note)
                }
            )
            .disposeOnViewModelDestroy()
    }

    fun onFilterClick() {
        viewCommands.onNext(ViewCommand.OpenFilterDialog)
    }

    fun onClearFilterClick() {
        val currentState = screenState.value ?: return
        if (currentState is Content) return
        val filteredContentState = screenState.value as? FilteredContent ?: return
        val contentViewState = Content(filteredContentState.notes)
        updateViewState(contentViewState)
    }

    fun onFilterTypeClick(markerType: MarkerTypeUi) {
        val contentState = screenState.value as? BaseContent ?: return
        val filteredNotes = filterNotesByMarkerType(contentState.notes, markerType)
        val newViewState = FilteredContent(
            notes = contentState.notes,
            filteredNotes = filteredNotes,
            currentMarkerType = markerType
        )
        updateViewState(newViewState)
    }

    private fun loadNotes() {
        notepadUseCase.getNotes()
            .map { noteMapper.fromDomainToUi(it) }
            .subscribe(
                {
                    showNotes(it)
                }, {
                    Timber.tag(TAG).e(it)
                    showErrorMessage(R.string.error_while_load_data_from_db)
                }
            )
            .disposeOnViewModelDestroy()
    }

    private fun showNotes(notes: List<NoteUi>) {
        when (val currentState = screenState.value) {
            is FilteredContent -> {
                val filteredNotes = filterNotesByMarkerType(notes, currentState.currentMarkerType)
                val newViewState = currentState.copy(
                    notes = notes,
                    filteredNotes = filteredNotes
                )
                updateViewState(newViewState)
            }
            else -> {
                val newContentState = Content(notes)
                updateViewState(newContentState)
            }
        }
    }

    private fun filterNotesByMarkerType(notes: List<NoteUi>, type: MarkerTypeUi): List<NoteUi> {
        return notes
            .filter { it.markerType == type }
    }

    private fun updateViewState(newViewState: NotepadViewState) {
        screenState.value = newViewState
    }
}
