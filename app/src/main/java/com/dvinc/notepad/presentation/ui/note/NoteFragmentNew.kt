/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.note

import android.os.Bundle
import android.view.View
import com.dvinc.notepad.R
import com.dvinc.notepad.common.extension.observe
import com.dvinc.notepad.common.extension.obtainViewModel
import com.dvinc.notepad.common.viewmodel.ViewModelFactory
import com.dvinc.notepad.di.DiProvider
import com.dvinc.notepad.presentation.ui.base.BaseFragment
import javax.inject.Inject

class NoteFragmentNew : BaseFragment() {

    companion object {
        const val NOTE_ID = "noteId"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: NoteViewModel

    override fun getFragmentLayoutId(): Int = R.layout.fragment_note

    private val noteId: Long? by lazy { arguments?.getLong(NOTE_ID, 0) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()
        initViewModel()
    }

    private fun injectDependencies() {
        DiProvider.appComponent.inject(this)
    }

    private fun initViewModel() {
        viewModel = obtainViewModel(viewModelFactory)
        observe(viewModel.state, ::handleViewState)
        viewModel.initNote(noteId)
    }

    private fun handleViewState(viewState: NoteViewState) {

    }
}
