package com.dvinc.notepad.presentation.ui.notepad

import androidx.lifecycle.MutableLiveData
import com.dvinc.notepad.R
import com.dvinc.notepad.domain.usecase.notepad.NotepadUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.model.NoteUi
import com.dvinc.notepad.presentation.ui.base.BaseViewModel
import com.dvinc.notepad.presentation.ui.base.ViewCommand.OpenNoteScreen
import com.dvinc.notepad.presentation.ui.notepad.NotepadViewState.Content
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
                    showErrorMessage(R.string.error_while_deleting_note)
                    Timber.tag(TAG).e(it)
                }
            )
            .disposeOnViewModelDestroy()
    }

    private fun loadNotes() {
        notepadUseCase.getNotes()
            .map { noteMapper.fromDomainToUi(it) }
            .subscribe(
                {
                    showNotes(it)
                },
                {
                    showErrorMessage(R.string.error_while_load_data_from_db)
                    Timber.tag(TAG).e(it)
                }
            )
            .disposeOnViewModelDestroy()
    }

    private fun showNotes(notes: List<NoteUi>) {
        val newContentState = Content(notes)
        updateViewState(newContentState)
    }

    private fun updateViewState(newViewState: NotepadViewState) {
        screenState.value = newViewState
    }
}
