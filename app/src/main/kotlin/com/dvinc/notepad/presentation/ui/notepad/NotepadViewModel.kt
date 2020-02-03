package com.dvinc.notepad.presentation.ui.notepad

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dvinc.core.extension.safeLaunch
import com.dvinc.notepad.R
import com.dvinc.notepad.domain.usecase.notepad.NotepadUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

class NotepadViewModel @Inject constructor(
    private val notepadUseCase: NotepadUseCase,
    private val noteMapper: NotePresentationMapper
) : BaseViewModel() {

    companion object {
        private const val TAG = "NotepadViewModel"
    }

    val viewState = MutableLiveData<NotepadViewState>()
        .apply {
            value = NotepadViewState()
        }

    init {
        loadNotes()
    }

    fun onNoteItemClick(noteId: Long) {
        val openNoteScreenCommand = OpenNoteScreen(noteId = noteId)
        viewCommands.onNext(openNoteScreenCommand)
    }

    fun onNoteDelete(noteId: Long) {
        viewModelScope.safeLaunch(
            launchBlock = {
                notepadUseCase.deleteNote(noteId)
            },
            onSuccess = {
                showMessage(R.string.note_successfully_deleted)
            },
            onError = {
                showErrorMessage(R.string.error_while_deleting_note)
                Timber.tag(TAG).e(it)
            }
        )
    }

    private fun loadNotes() {
        notepadUseCase.getNotes()
            .onEach {
                val notes = noteMapper.fromDomainToUi(it)
                updateViewState { state ->
                    state.copy(
                        notes = notes,
                        isStubViewVisible = notes.isEmpty()
                    )
                }
            }
            .catch {
                showErrorMessage(R.string.error_while_load_data_from_db)
                Timber.tag(TAG).e(it)
            }
            .launchIn(viewModelScope)
    }

    private inline fun updateViewState(update: (NotepadViewState) -> NotepadViewState) {
        viewState.value = update.invoke(requireNotNull(viewState.value))
    }
}
