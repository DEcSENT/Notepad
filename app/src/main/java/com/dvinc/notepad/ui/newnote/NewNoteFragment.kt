/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.newnote

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import com.dvinc.notepad.App
import com.dvinc.notepad.R
import kotlinx.android.synthetic.main.fragment_new_note.*
import javax.inject.Inject

class NewNoteFragment : DialogFragment(), NewNoteView {

    @Inject lateinit var presenter: NewNotePresenter

    companion object {
        val TAG = "NewNoteFragment"
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_note, container)

        //Hide title
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        (context?.applicationContext as App).appComponent.inject(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAddNewNoteButton()
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    override fun closeScreen() {
        dismiss()
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun setupAddNewNoteButton() {
        btAddNewNote.setOnClickListener {
            val name = etNewNoteName.text.toString()
            val content = etNewNoteContent.text.toString()
            val currentTime = System.currentTimeMillis()
            presenter.saveNewNote(name, content, currentTime)
        }
    }
}
