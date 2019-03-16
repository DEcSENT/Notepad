package com.dvinc.notepad.presentation.ui.notepad

import androidx.lifecycle.MutableLiveData
import com.dvinc.notepad.common.extension.onNext
import com.dvinc.notepad.common.resource.ResourceProvider
import com.dvinc.notepad.domain.usecase.notepad.NotepadUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.model.NoteUi
import com.dvinc.notepad.presentation.ui.base.BaseViewModel
import com.dvinc.notepad.presentation.ui.notepad.NotepadViewState.Content
import com.dvinc.notepad.presentation.ui.notepad.NotepadViewState.EmptyContent
import javax.inject.Inject

//todo finish this then DI will be ready
class NotepadViewModel @Inject constructor(
    private val notepadUseCase: NotepadUseCase,
    private val noteMapper: NotePresentationMapper,
    private val resProvider: ResourceProvider
) : BaseViewModel() {

    val state = MutableLiveData<NotepadViewState>()

    init {
        loadNotes()
    }

    fun onNoteItemClick(note: NoteUi) {
        //todo handle clicked item
    }

    private fun loadNotes() {
        notepadUseCase.getNotes()
            .map { noteMapper.fromDomainToUi(it) }
            .subscribe(
                { notes ->
                    if (notes.isEmpty()) {
                        state.onNext(EmptyContent)
                    } else {
                        val content = Content(notes)
                        state.onNext(content)
                    }
                }, {
                    //todo show and log error
                }
            )
            .disposeOnViewModelDestroy()
    }
}
