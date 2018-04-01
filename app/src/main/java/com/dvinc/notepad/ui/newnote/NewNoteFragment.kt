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
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.dvinc.notepad.App
import com.dvinc.notepad.R
import javax.inject.Inject

class NewNoteFragment : DialogFragment(), NewNoteView {

    @BindView(R.id.et_new_note_name) lateinit var noteName: EditText
    @BindView(R.id.et_new_note_content) lateinit var noteContent: EditText

    @Inject lateinit var presenter: NewNotePresenter

    private lateinit var unbinder: Unbinder

    companion object {
        val TAG = "NewNoteFragment"
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_note, container)
        unbinder = ButterKnife.bind(this, view)

        //Hide title
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        (context.applicationContext as App).appComponent.inject(this)

        return view
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

    @OnClick(R.id.bt_new_note_add)
    fun onAddButtonClick(view: View) {
        val name = noteName.text.toString()
        val content = noteContent.text.toString()
        val currentTime = System.currentTimeMillis()

        presenter.saveNewNote(name, content, currentTime)
    }
}
