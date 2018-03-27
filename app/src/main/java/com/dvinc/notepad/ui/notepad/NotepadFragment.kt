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

class NotepadFragment : BaseFragment(), NotepadView {

    @BindView(R.id.rv_notepad) lateinit var rvNotes: RecyclerView

    private var notePadPresenter: NotepadPresenter? = null

    companion object {
        val TAG = "NotepadFragment"
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvNotes.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        notePadPresenter = NotepadPresenter()
    }

    override fun onResume() {
        super.onResume()
        notePadPresenter?.attachView(this)
        notePadPresenter?.initNotes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        notePadPresenter?.detachView()
    }

    override fun getFragmentLayoutId(): Int {
        return R.layout.fragment_notepad
    }

    override fun showEmptyView() {
        TODO("not implemented")
    }

    override fun showSuccessMessage() {
        TODO("not implemented")
    }

    override fun showError(message: String) {
        TODO("not implemented")
    }

    override fun showNotes(notes: List<Note>) {
        val adapter = NotesAdapter(notes)
        rvNotes.adapter = adapter
    }
}
