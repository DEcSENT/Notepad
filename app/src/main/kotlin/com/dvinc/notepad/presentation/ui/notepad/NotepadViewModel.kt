package com.dvinc.notepad.presentation.ui.notepad

import androidx.lifecycle.MutableLiveData
import com.dvinc.notepad.R
import com.dvinc.notepad.common.extension.onNext
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

    val state = MutableLiveData<NotepadViewState>()

    init {
        loadNotes()
    }

    fun onNoteItemClick(note: NoteUi) {
        val openNoteScreenCommand = OpenNoteScreen(noteId = note.id)
        commands.onNext(openNoteScreenCommand)
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
        commands.onNext(ViewCommand.OpenFilterDialog)
    }

    fun onClearFilterClick() {
        val filteredContentState = state.value as? BaseContent ?: return
        val fullContent = Content(filteredContentState.notes)
        state.onNext(fullContent)
    }

    fun onFilterTypeClick(markerType: MarkerTypeUi) {
        val contentState = state.value as? BaseContent ?: return
        val filteredNotes = filterNotesByMarkerType(contentState.notes, markerType)
        val filteredContent = FilteredContent(
            notes = contentState.notes,
            filteredNotes = filteredNotes
        )
        state.onNext(filteredContent)
    }

    private fun loadNotes() {
        notepadUseCase.getNotes()
            .map { noteMapper.fromDomainToUi(it) }
            .subscribe(
                { notes ->
                    val content = Content(notes)
                    state.onNext(content)
                }, {
                    Timber.tag(TAG).e(it)
                    showErrorMessage(R.string.error_while_load_data_from_db)
                }
            )
            .disposeOnViewModelDestroy()
    }

    private fun filterNotesByMarkerType(notes: List<NoteUi>, type: MarkerTypeUi): List<NoteUi> {
        return notes
            .filter { it.markerType == type }
    }
}
