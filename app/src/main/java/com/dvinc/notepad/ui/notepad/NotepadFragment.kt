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
import android.widget.Toast
import butterknife.BindView
import com.dvinc.notepad.App
import com.dvinc.notepad.R
import com.dvinc.notepad.data.database.entity.Note
import com.dvinc.notepad.ui.base.BaseFragment
import javax.inject.Inject

class NotepadFragment : BaseFragment(), NotepadView {

    @BindView(R.id.rv_notepad) lateinit var rvNotes: RecyclerView

    @Inject lateinit var notePadPresenter: NotepadPresenter

    companion object {
        val TAG = "NotepadFragment"
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvNotes.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        notePadPresenter.attachView(this)
        notePadPresenter.initNotes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        notePadPresenter.detachView()
    }

    override fun getFragmentLayoutId(): Int {
        return R.layout.fragment_notepad
    }

    override fun showEmptyView() {
        TODO("not implemented")
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showNotes(notes: List<Note>) {
        val adapter = NotesAdapter(notes)
        rvNotes.adapter = adapter
    }
}
