/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.notepad

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.dvinc.notepad.R
import com.dvinc.notepad.common.extension.makeGone
import com.dvinc.notepad.common.extension.makeVisible
import com.dvinc.notepad.common.extension.observe
import com.dvinc.notepad.common.extension.obtainViewModel
import com.dvinc.notepad.common.viewmodel.ViewModelFactory
import com.dvinc.notepad.di.DiProvider
import com.dvinc.notepad.presentation.adapter.notepad.NotepadAdapter
import com.dvinc.notepad.presentation.adapter.notepad.NotepadSwipeToDeleteCallback
import com.dvinc.notepad.presentation.model.NoteUi
import com.dvinc.notepad.presentation.ui.base.BaseFragment
import com.dvinc.notepad.presentation.ui.base.ViewCommand
import com.dvinc.notepad.presentation.ui.base.ViewCommand.OpenNoteScreen
import com.dvinc.notepad.presentation.ui.base.ViewCommand.ShowMessage
import com.dvinc.notepad.presentation.ui.note.NoteFragment
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_bottom_app_bar as bottomBar
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_fab as bottomBarFab
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_recycler as notesRecycler
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_stub_container as stubContainer

class NotepadFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: NotepadViewModel

    private val notesAdapter: NotepadAdapter = NotepadAdapter()

    private val noteItemClickListener = object : NotepadAdapter.ItemClickListener {
        override fun onItemClick(note: NoteUi) {
            viewModel.onNoteItemClick(note)
        }
    }

    private val noteItemSwipeListener: (note: NoteUi) -> Unit = {
        viewModel.onNoteDelete(it)
    }

    override fun getFragmentLayoutId(): Int = R.layout.fragment_notepad

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
    }

    private fun initViews() {
        setupNoteRecycler()
        setupFabButton()
        setupNotesAdapterClickListener()
        setupBottomBar()
    }

    private fun setupNoteRecycler() {
        with(notesRecycler) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notesAdapter
        }
        val notepadTouchCallback = NotepadSwipeToDeleteCallback(notesAdapter, noteItemSwipeListener)
        val swipeToDeleteTouchHelper = ItemTouchHelper(notepadTouchCallback)
        swipeToDeleteTouchHelper.attachToRecyclerView(notesRecycler)
    }

    private fun setupFabButton() {
        bottomBarFab.setOnClickListener {
            activity?.let {
                findNavController(it, R.id.nav_host_fragment)
                    .navigate(R.id.action_notepadFragment_to_noteFragment)
            }
        }
    }

    private fun setupNotesAdapterClickListener() {
        notesAdapter.setOnItemClickListener(noteItemClickListener)
    }

    private fun setupBottomBar() {
        bottomBar.replaceMenu(R.menu.notepad_bottom_bar_menu)
        bottomBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.fragment_notepad_filter_menu_item -> {
                    Toast.makeText(requireContext(), "Filter will be here", Toast.LENGTH_LONG)
                        .show()
                }
            }
            true
        }
    }

    private fun handleViewState(viewState: NotepadViewState) {
        when (viewState) {
            is NotepadViewState.Content -> {
                showNotes(viewState.notes)
                stubContainer.makeGone()
            }
            is NotepadViewState.EmptyContent -> {
                showNotes(emptyList())
                stubContainer.makeVisible()
            }
        }
    }

    private fun handleViewCommand(viewCommand: ViewCommand) {
        when(viewCommand) {
            is OpenNoteScreen -> goToNoteScreen(viewCommand.noteId)
            is ShowMessage -> {
                showMessage(
                    messageResId = viewCommand.messageResId,
                    anchorView = bottomBarFab
                )
            }
        }
    }

    private fun showNotes(notes: List<NoteUi>) {
        notesAdapter.updateNotes(notes)
    }

    private fun goToNoteScreen(noteId: Long) {
        val bundle = Bundle().apply { putLong(NoteFragment.NOTE_ID, noteId) }
        findNavController(requireNotNull(view))
            .navigate(R.id.action_notepadFragment_to_noteFragment, bundle)
    }
}
