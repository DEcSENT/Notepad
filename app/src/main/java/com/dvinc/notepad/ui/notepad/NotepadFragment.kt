/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.notepad

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import butterknife.BindView
import com.dvinc.notepad.R
import com.dvinc.notepad.data.Note
import com.dvinc.notepad.ui.base.BaseFragment

class NotepadFragment : BaseFragment() {

    @BindView(R.id.rv_notepad) lateinit var rvNotes: RecyclerView

    companion object {
        val TAG = "NotepadFragment"
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvNotes.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        val users = ArrayList<Note>()
        users.add(Note("Note 1", "Well, Kotlin isn't looks so good...", System.currentTimeMillis()))
        users.add(Note("Note 2", "But maybe sometimes i will learn it...", System.currentTimeMillis()))

        val adapter = NotesAdapter(users)
        rvNotes.adapter = adapter
    }

    override fun getFragmentLayoutId(): Int {
        return R.layout.fragment_notepad
    }
}
