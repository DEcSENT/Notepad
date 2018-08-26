/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.filter

import android.app.Dialog
import android.graphics.Color
import android.support.v4.app.DialogFragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.dvinc.notepad.R
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import com.dvinc.notepad.NotepadApplication
import com.dvinc.notepad.presentation.adapter.item.MarkerItem
import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.dialog_filter.dialog_filter_recycler as filterRecycler
import kotlinx.android.synthetic.main.dialog_filter.dialog_filter_cancel_button as cancelButton
import kotlinx.android.synthetic.main.dialog_filter.dialog_filter_background as dialogShadow
import javax.inject.Inject

class FilterDialogFragment : DialogFragment(), FilterView {

    companion object {

        const val TAG = "FilterDialogFragment"

        fun newInstance() = FilterDialogFragment()
    }

    @Inject
    lateinit var presenter: FilterPresenter

    private val markersAdapter = GroupAdapter<ViewHolder>()

    override fun onStart() {
        super.onStart()
        with(dialog) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = View.inflate(activity, R.layout.dialog_filter, null)
        val dialog = Dialog(activity, R.style.DialogFragmentNoTitleStyle)
        dialog.setContentView(view)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectPresenter()
        setupShadow()
        setupCancelButton()
        setupMarkersList()
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
        presenter.initMarkers()
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    override fun showMarkers(markers: List<MarkerTypeUi>) {
        markersAdapter.clear()
        markersAdapter.addAll(markers.map {
            MarkerItem(it)
        })
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun sendTypeToFilter(type: MarkerTypeUi) {
        (targetFragment as? FilterClickListener)?.loadNotesBySpecificMarkerType(type)
    }

    override fun closeDialog() {
        dismiss()
    }

    private fun injectPresenter() {
        (context?.applicationContext as NotepadApplication).appComponent.inject(this)
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

    private fun setupMarkersList() {
        filterRecycler.adapter = markersAdapter
        markersAdapter.setOnItemClickListener { item, _ ->
            if (item is MarkerItem) {
                presenter.onMarkerItemClick(item.marker)
            }
        }
    }
}
