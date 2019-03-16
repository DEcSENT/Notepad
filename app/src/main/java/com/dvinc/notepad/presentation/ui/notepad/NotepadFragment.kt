/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.notepad

import android.os.Bundle
import android.view.View
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
import com.dvinc.notepad.presentation.ui.note.NoteFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_empty_view as emptyView
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_fab as fab
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_filter_icon as filterIcon
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_recycler as notesRecycler
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_small_filter_icon as filterSmallIcon

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
    }

    private fun initViews() {
        setupNoteRecycler()
        setupFabButton()
        setupNotesAdapterClickListener()
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

    private fun handleViewState(viewState: NotepadViewState) {
        when (viewState) {
            is NotepadViewState.Content -> showNotes(viewState.notes)
        }
    }

    private fun showNotes(notes: List<NoteUi>) {
        noteAdapter.update(
            notes.map {
                NoteItem(it)
            }
        )
    }

    //todo handle this navigation later
    private fun goToNoteScreen(noteId: Long) {
        val bundle = Bundle().apply { putLong(NoteFragment.NOTE_ID, noteId) }
        activity?.let {
            findNavController(it, R.id.nav_host_fragment)
                .navigate(R.id.action_notepadFragment_to_noteFragment, bundle)
        }
    }
}
