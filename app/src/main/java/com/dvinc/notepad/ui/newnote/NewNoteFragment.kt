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
import butterknife.ButterKnife
import butterknife.Unbinder
import com.dvinc.notepad.R

class NewNoteFragment : DialogFragment() {

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

        return view
    }
}
