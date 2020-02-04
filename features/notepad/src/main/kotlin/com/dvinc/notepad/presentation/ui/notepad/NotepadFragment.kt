/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.notepad

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.dvinc.core.extension.observe
import com.dvinc.core.extension.toggleGone
import com.dvinc.core.recycler.SpaceItemDecorator
import com.dvinc.core.ui.BaseFragment
import com.dvinc.core.ui.ShowMessage
import com.dvinc.core.ui.ViewCommand
import com.dvinc.notepad.R
import com.dvinc.notepad.di.component.NotepadComponent
import com.dvinc.notepad.presentation.adapter.notepad.NotepadAdapter
import com.dvinc.notepad.presentation.adapter.notepad.NotepadSwipeToDeleteCallback
import com.dvinc.notepad.presentation.model.NoteUi
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_bottom_app_bar as bottomBar
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_fab as bottomBarFab
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_recycler as notesRecycler
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_stub_container as stubContainer

class NotepadFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: Provider<NotepadViewModel>

    private val viewModel by lazy { viewModelFactory.get() }

    private val notesAdapter: NotepadAdapter = NotepadAdapter()

    private val noteItemClickListener = object : NotepadAdapter.ItemClickListener {
        override fun onItemClick(note: NoteUi) {
            viewModel.onNoteItemClick(note.id)
        }
    }

    private val noteItemSwipeListener: (note: NoteUi) -> Unit = {
        viewModel.onNoteDelete(it.id)
    }

    override fun getFragmentLayoutId(): Int = R.layout.fragment_notepad

    override fun injectDependencies() {
        NotepadComponent.Builder
            .build(appComponent)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerViewModel()
        initViews()
    }

    private fun observerViewModel() {
        observe(viewModel.viewState, ::handleViewState)
        observe(viewModel.viewCommands, ::handleViewCommand)
    }

    private fun initViews() {
        setupNotepadRecycler()
        setupFabButton()
        setupNotesAdapterClickListener()
        setupBottomBar()
    }

    private fun setupNotepadRecycler() {
        with(notesRecycler) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notesAdapter
            addItemDecoration(SpaceItemDecorator())
        }
        val notepadTouchCallback = NotepadSwipeToDeleteCallback(notesAdapter, noteItemSwipeListener)
        val swipeToDeleteTouchHelper = ItemTouchHelper(notepadTouchCallback)
        swipeToDeleteTouchHelper.attachToRecyclerView(notesRecycler)
    }

    private fun setupFabButton() {
        bottomBarFab.setOnClickListener {
            viewModel.onNewNoteClick()
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
                    // TODO(dv): handle filter click
                }
            }
            true
        }
    }

    private fun handleViewState(viewState: NotepadViewState) {
        with(viewState) {
            showNotes(notes)
            showStub(isStubViewVisible)
        }
    }

    override fun handleViewCommand(viewCommand: ViewCommand) {
        super.handleViewCommand(viewCommand)
        when (viewCommand) {
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

    private fun showStub(isVisible: Boolean) {
        stubContainer.toggleGone(isVisible)
    }
}
