/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.note

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.dvinc.notepad.R
import com.dvinc.notepad.common.extension.observe
import com.dvinc.notepad.common.extension.obtainViewModel
import com.dvinc.notepad.common.viewmodel.ViewModelFactory
import com.dvinc.notepad.di.DiProvider
import com.dvinc.notepad.presentation.adapter.MarkerAdapter
import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.dvinc.notepad.presentation.ui.base.BaseFragment
import com.dvinc.notepad.presentation.ui.base.ViewCommand
import com.dvinc.notepad.presentation.ui.base.ViewCommand.CloseNoteScreen
import com.dvinc.notepad.presentation.ui.base.ViewCommand.ShowErrorMessage
import com.dvinc.notepad.presentation.ui.note.NoteViewState.ExistingNoteViewState
import com.dvinc.notepad.presentation.ui.note.NoteViewState.NewNoteViewState
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_note_new.fragment_note_content as noteContent
import kotlinx.android.synthetic.main.fragment_note_new.fragment_note_name as noteName
import kotlinx.android.synthetic.main.fragment_note_new.fragment_note_save_button as saveNoteButton
import kotlinx.android.synthetic.main.fragment_note_new.fragment_note_toolbar as toolbar
import kotlinx.android.synthetic.main.fragment_note_new.fragment_note_type_spinner as noteTypeSpinner

class NoteFragmentNew : BaseFragment() {

    companion object {
        const val NOTE_ID = "noteId"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: NoteViewModel

    override fun getFragmentLayoutId(): Int = R.layout.fragment_note_new

    private val noteId: Long? by lazy { arguments?.getLong(NOTE_ID, 0) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()
        initViewModel()
        initViews()
    }

    private fun injectDependencies() {
        DiProvider.appComponent.inject(this)
    }

    private fun initViewModel() {
        viewModel = obtainViewModel(viewModelFactory)
        observe(viewModel.state, ::handleViewState)
        observe(viewModel.commands, ::handleViewCommand)
        viewModel.initNote(noteId)
    }

    private fun initViews() {
        setupBackButton()
        setupSaveButton()
    }

    private fun setupBackButton() {
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(requireNotNull(view)).navigateUp()
        }
    }

    private fun setupSaveButton() {
        saveNoteButton.setOnClickListener {
            val noteName = noteName.text.toString()
            val noteContent = noteContent.text.toString()
            val markerType = noteTypeSpinner.selectedItem as MarkerTypeUi
            viewModel.onSaveButtonClick(noteName, noteContent, markerType)
        }
    }

    private fun handleViewState(viewState: NoteViewState) {
        when (viewState) {
            is NewNoteViewState -> showNewNote(viewState)
            is ExistingNoteViewState -> showExistingNote(viewState)
        }
    }

    private fun handleViewCommand(viewCommand: ViewCommand) {
        when (viewCommand) {
            is CloseNoteScreen -> {
                Navigation.findNavController(requireNotNull(view)).navigateUp()
            }
            is ShowErrorMessage -> {
                showErrorMessage(viewCommand.messageResId)
            }
        }
    }

    private fun showNewNote(viewState: NewNoteViewState) {
        val addNoteText = getString(R.string.note_add)
        saveNoteButton.text = addNoteText
        fillMarkersView(viewState.availableMarkers)
    }

    private fun showExistingNote(viewState: ExistingNoteViewState) {
        val editNoteText = getString(R.string.note_edit)
        saveNoteButton.text = editNoteText
        val note = viewState.note
        noteName.setText(note.name)
        noteContent.setText(note.content)
        fillMarkersView(viewState.availableMarkers)
        //TODO(dv): Think about this - using ordinal isn't good idea
        noteTypeSpinner.setSelection(note.markerType.ordinal)
    }

    private fun fillMarkersView(markers: List<MarkerTypeUi>) {
        val adapter = MarkerAdapter(context, R.layout.item_note_marker, markers)
        noteTypeSpinner.adapter = adapter
    }
}
