/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.filter

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.dvinc.notepad.R
import com.dvinc.notepad.common.extension.observe
import com.dvinc.notepad.common.extension.obtainViewModel
import com.dvinc.notepad.common.viewmodel.ViewModelFactory
import com.dvinc.notepad.di.DiProvider
import com.dvinc.notepad.presentation.adapter.item.MarkerItem
import com.dvinc.notepad.presentation.ui.base.ViewCommand
import com.dvinc.notepad.presentation.ui.base.ViewCommand.CloseFilterDialogWithClearResult
import com.dvinc.notepad.presentation.ui.base.ViewCommand.CloseFilterDialogWithSelectedFilterType
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import javax.inject.Inject
import kotlinx.android.synthetic.main.dialog_filter.dialog_filter_background as dialogShadow
import kotlinx.android.synthetic.main.dialog_filter.dialog_filter_cancel_button as cancelButton
import kotlinx.android.synthetic.main.dialog_filter.dialog_filter_clear_button as clearButton
import kotlinx.android.synthetic.main.dialog_filter.dialog_filter_recycler as filterRecycler

class FilterDialogFragment : DialogFragment() {

    companion object {

        const val TAG = "FilterDialogFragment"

        fun newInstance(targetFragment: Fragment): FilterDialogFragment {
            return FilterDialogFragment().apply {
                setTargetFragment(targetFragment, 0)
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: FilterViewModel

    private val markersAdapter = GroupAdapter<ViewHolder>()

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = View.inflate(activity, R.layout.dialog_filter, null)
        val dialog = Dialog(requireContext(), R.style.DialogFragmentNoTitleStyle)
        dialog.setContentView(view)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        initViewModel()
        setupShadow()
        setupCancelButton()
        setupClearFilterButton()
        setupMarkersList()
    }

    private fun injectDependencies() {
        DiProvider.appComponent.inject(this)
    }

    private fun initViewModel() {
        viewModel = obtainViewModel(viewModelFactory)
        observe(viewModel.state, ::handleViewState)
        observe(viewModel.commands, ::handleViewCommand)
    }

    private fun handleViewState(viewState: FilterViewState) {
        val markerItems = viewState.markers.map {
            MarkerItem(it)
        }
        markersAdapter.update(markerItems)
    }

    private fun handleViewCommand(viewCommand: ViewCommand) {
        when (viewCommand) {
            is CloseFilterDialogWithClearResult -> {
                (targetFragment as? FilterClickListener)?.loadAllNotes()
                dismiss()
            }
            is CloseFilterDialogWithSelectedFilterType -> {
                (targetFragment as? FilterClickListener)
                    ?.loadNotesBySpecificMarkerType(viewCommand.markerType)
                dismiss()
            }
        }
    }

    /*
    * Simple, but strange, way to realise shadow and dismiss dialog by clicking outside visible dialog area.
    * Google use similar way in their Google IO 2018 app.
    */
    private fun setupShadow() {
        dialogShadow.setOnClickListener {
            dismiss()
        }
    }

    private fun setupCancelButton() {
        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setupClearFilterButton() {
        clearButton.setOnClickListener {
            viewModel.onClearButtonClick()
        }
    }

    private fun setupMarkersList() {
        filterRecycler.adapter = markersAdapter
        markersAdapter.setOnItemClickListener { item, _ ->
            if (item is MarkerItem) {
                viewModel.onMarkerItemClick(item)
            }
        }
    }
}
