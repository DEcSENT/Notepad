package com.dvinc.notepad.presentation.ui.archive

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dvinc.core.extension.update
import com.dvinc.core.ui.BaseViewModel
import com.dvinc.notepad.R
import com.dvinc.notepad.domain.usecase.archive.ArchiveUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

class ArchiveViewModel @Inject constructor(
    private val archiveUseCase: ArchiveUseCase,
    private val noteMapper: NotePresentationMapper
) : BaseViewModel() {

    val viewState = MutableLiveData(ArchiveViewState())

    init {
        loadArchive()
    }

    private fun loadArchive() {
        archiveUseCase.getArchivedNotes()
            .onEach {
                val notes = noteMapper.fromDomainToUi(it)
                viewState.update { state ->
                    state.copy(
                        archivedNotes = notes,
                        isStubViewVisible = notes.isEmpty()
                    )
                }
            }
            .catch {
                showErrorMessage(R.string.error_while_load_data_from_db)
                Timber.e(it)
            }
            .launchIn(viewModelScope)
    }
}
