/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.note

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.dvinc.core.extension.observe
import com.dvinc.core.extension.viewModels
import com.dvinc.core.ui.BaseFragment
import com.dvinc.core.ui.ShowErrorMessage
import com.dvinc.core.ui.ViewCommand
import com.dvinc.notepad.R
import com.dvinc.notepad.di.component.DaggerNotepadComponent
import com.dvinc.notepad.ui.note.NoteViewState.ExistingNoteViewState
import com.dvinc.notepad.ui.note.NoteViewState.NewNoteViewState
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_note.fragment_note_content as noteContent
import kotlinx.android.synthetic.main.fragment_note.fragment_note_name as noteName
import kotlinx.android.synthetic.main.fragment_note.fragment_note_save_button as saveNoteButton
import kotlinx.android.synthetic.main.fragment_note.fragment_note_toolbar as toolbar

class NoteFragment : BaseFragment(layoutResId = R.layout.fragment_note) {

    @Inject
    lateinit var viewModelFactory: NoteViewModel.Factory

    private val viewModel: NoteViewModel by viewModels { viewModelFactory.get(navArgs.noteId) }

    private val navArgs: NoteFragmentArgs by navArgs()

    override fun injectDependencies() {
        DaggerNotepadComponent.factory()
            .create(appComponent)
            .inject(this)
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
            viewModel.onBackClick()
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
            is NewNoteViewState -> showNewNote()
            is ExistingNoteViewState -> showExistingNote(viewState)
        }
    }

    override fun handleViewCommand(viewCommand: ViewCommand) {
        super.handleViewCommand(viewCommand)
        when (viewCommand) {
            is ShowErrorMessage -> {
                showErrorMessage(viewCommand.messageResId)
            }
        }
    }

    private fun showNewNote() {
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
