/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.archive.ui.archive

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dvinc.archive.R
import com.dvinc.archive.di.component.DaggerArchiveComponent
import com.dvinc.base.notepad.presentation.adapter.notepad.NotepadAdapter
import com.dvinc.core.extension.observe
import com.dvinc.core.extension.toggleGone
import com.dvinc.core.extension.viewModels
import com.dvinc.core.recycler.SpaceItemDecorator
import com.dvinc.core.ui.BaseFragment
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.android.synthetic.main.fragment_archive.fragment_archive_recycler as archiveRecycle
import kotlinx.android.synthetic.main.fragment_archive.fragment_archive_stub_text as stubContainer

class ArchiveFragment : BaseFragment(layoutResId = R.layout.fragment_archive) {

    @Inject
    lateinit var viewModelProvider: Provider<ArchiveViewModel>

    private val viewModel by viewModels { viewModelProvider.get() }

    private val notesAdapter: NotepadAdapter by lazy { NotepadAdapter() }

    override fun injectDependencies() {
        DaggerArchiveComponent.factory()
            .create(appComponent)
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
        with(archiveRecycle) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notesAdapter
            addItemDecoration(SpaceItemDecorator())
        }
    }

    private fun handleViewState(viewState: ArchiveViewState) {
        notesAdapter.updateNotes(viewState.archivedNotes)
        stubContainer.toggleGone(viewState.isStubViewVisible)
    }
}