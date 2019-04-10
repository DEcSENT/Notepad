/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.notepad

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dvinc.notepad.R
import com.dvinc.notepad.common.extension.observe
import com.dvinc.notepad.common.extension.obtainViewModel
import com.dvinc.notepad.common.viewmodel.ViewModelFactory
import com.dvinc.notepad.di.DiProvider
import com.dvinc.notepad.presentation.adapter.item.NoteItem
import com.dvinc.notepad.presentation.model.NoteUi
import com.dvinc.notepad.presentation.ui.base.BaseFragment
import com.dvinc.notepad.presentation.ui.base.ViewCommand
import com.dvinc.notepad.presentation.ui.base.ViewCommand.OpenNoteScreen
import com.dvinc.notepad.presentation.ui.base.ViewCommand.ShowMessage
import com.dvinc.notepad.presentation.ui.note.NoteFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_bottom_app_bar as bottomBar
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_fab as fab
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_recycler as notesRecycler

class NotepadFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: NotepadViewModel

    private val noteAdapter: GroupAdapter<ViewHolder> = GroupAdapter()

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
            adapter = noteAdapter
        }
    }

    private fun setupFabButton() {
        fab.setOnClickListener {
            activity?.let {
                findNavController(it, R.id.nav_host_fragment)
                    .navigate(R.id.action_notepadFragment_to_noteFragment)
            }
        }

        //Hiding fab by scroll
        notesRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0)
                    fab.hide()
                else if (dy < 0)
                    fab.show()
            }
        })
    }

    private fun setupNotesAdapterClickListener() {
        noteAdapter.setOnItemClickListener { item, _ ->
            if (item is NoteItem) {
                viewModel.onNoteItemClick(item.note)
            }
        }
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
            is NotepadViewState.Content -> showNotes(viewState.notes)
        }
    }

    private fun handleViewCommand(viewCommand: ViewCommand) {
        when(viewCommand) {
            is OpenNoteScreen -> goToNoteScreen(viewCommand.noteId)
            is ShowMessage -> {
                showMessage(
                    messageResId = viewCommand.messageResId,
                    anchorView = bottomBar
                )
            }
        }
    }

    private fun showNotes(notes: List<NoteUi>) {
        noteAdapter.update(
            notes.map {
                NoteItem(it)
            }
        )
    }

    private fun goToNoteScreen(noteId: Long) {
        val bundle = Bundle().apply { putLong(NoteFragment.NOTE_ID, noteId) }
        findNavController(requireNotNull(view))
            .navigate(R.id.action_notepadFragment_to_noteFragment, bundle)
    }
}
