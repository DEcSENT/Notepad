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
import com.dvinc.notepad.NotepadApplication
import javax.inject.Inject

class FilterDialogFragment : DialogFragment(), FilterView {

    companion object {

        const val TAG = "FilterDialogFragment"

        fun newInstance() = FilterDialogFragment()
    }

    @Inject
    lateinit var presenter: FilterPresenter

    override fun onStart() {
        super.onStart()
        with(dialog) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
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

    override fun showMarkers() {
        //TODO: show markers
    }

    private fun injectPresenter() {
        (context?.applicationContext as NotepadApplication).appComponent.inject(this)
    }
}
