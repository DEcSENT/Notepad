/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.note

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.dvinc.core.extension.observe
import com.dvinc.core.extension.viewModels
import com.dvinc.core.ui.BaseFragment
import com.dvinc.core.ui.ShowErrorMessage
import com.dvinc.core.ui.ViewCommand
import com.dvinc.notepad.R
import com.dvinc.notepad.presentation.ui.note.NoteViewState.ExistingNoteViewState
import com.dvinc.notepad.presentation.ui.note.NoteViewState.NewNoteViewState
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_note.fragment_note_content as noteContent
import kotlinx.android.synthetic.main.fragment_note.fragment_note_name as noteName
import kotlinx.android.synthetic.main.fragment_note.fragment_note_save_button as saveNoteButton
import kotlinx.android.synthetic.main.fragment_note.fragment_note_toolbar as toolbar

class NoteFragment : BaseFragment() {

    companion object {
        const val NOTE_ID = "noteId"
    }

    @Inject
    lateinit var viewModelFactory: NoteViewModel.Factory

    private val viewModel: NoteViewModel by viewModels { viewModelFactory.get(noteId) }

    private val noteId: Long? by lazy { arguments?.getLong(NOTE_ID, 0) }

    override fun getFragmentLayoutId(): Int = R.layout.fragment_note

    override fun injectDependencies() {
        //TODO(dv):
        //appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
    }

    private fun observeViewModel() {
        observe(viewModel.viewState, ::handleViewState)
        observe(viewModel.viewCommands, ::handleViewCommand)
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
            viewModel.onSaveButtonClick(noteName, noteContent)
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
    }

    private fun showExistingNote(viewState: ExistingNoteViewState) {
        val editNoteText = getString(R.string.note_edit)
        saveNoteButton.text = editNoteText
        val note = viewState.note
        noteName.setText(note.name)
        noteContent.setText(note.content)
    }
}
