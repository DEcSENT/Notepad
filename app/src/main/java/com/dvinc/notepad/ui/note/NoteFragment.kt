/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.note

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.dvinc.notepad.App
import com.dvinc.notepad.R
import com.dvinc.notepad.domain.model.NoteMarker
import com.dvinc.notepad.ui.adapters.NoteMarkersAdapter
import com.dvinc.notepad.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_note.*
import javax.inject.Inject

class NoteFragment : BaseFragment(), NoteView {

    @Inject lateinit var presenter: NotePresenter

    companion object {
        val TAG = "NoteFragment"
    }

    override fun getFragmentLayoutId(): Int = R.layout.fragment_note

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (context?.applicationContext as App).appComponent.inject(this)

        setupAddNewNoteButton()
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
        presenter.initView()
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    override fun closeScreen() {
        //TODO: Think about good navigation
        activity?.onBackPressed()
    }

    override fun showMarkers(markers: List<NoteMarker>) {
        val adapter = NoteMarkersAdapter(context, R.layout.item_note_marker, markers)
        spNoteType.adapter = adapter
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun setupAddNewNoteButton() {
        btAddNote.setOnClickListener {
            val name = etNoteName.text.toString()
            val content = etNoteContent.text.toString()
            val currentTime = System.currentTimeMillis()
            val markerColor = (spNoteType.selectedItem as NoteMarker).markerColor
            val markerText = (spNoteType.selectedItem as NoteMarker).markerName
            presenter.saveNewNote(name, content, currentTime, markerColor, markerText)
        }
    }
}
