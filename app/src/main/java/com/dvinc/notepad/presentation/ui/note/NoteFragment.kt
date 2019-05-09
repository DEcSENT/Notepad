/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.note

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import com.dvinc.notepad.R
import com.dvinc.notepad.common.extension.makeInvisible
import com.dvinc.notepad.common.extension.makeVisible
import com.dvinc.notepad.di.DiProvider
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.presentation.adapter.MarkerAdapter
import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.dvinc.notepad.presentation.ui.base.BaseFragment
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_note.fragment_note_add_button as addNoteButton
import kotlinx.android.synthetic.main.fragment_note.fragment_note_content as noteContent
import kotlinx.android.synthetic.main.fragment_note.fragment_note_edit_button as editNoteButton
import kotlinx.android.synthetic.main.fragment_note.fragment_note_name as noteName
import kotlinx.android.synthetic.main.fragment_note.fragment_note_toolbar as toolbar
import kotlinx.android.synthetic.main.fragment_note.fragment_note_type_spinner as noteTypeSpinner

@Deprecated("replace by new Note fragment")
class NoteFragment : BaseFragment(), NoteView {

    companion object {
        const val NOTE_ID = "noteId"
        const val NOTE_MARKER_ID = "noteMarkerId"
    }

    @Inject
    lateinit var presenter: NotePresenter

    private val noteId: Long? get() = arguments?.getLong(NOTE_ID, 0)

    private var noteInfoBundle: Bundle? = null

    override fun getFragmentLayoutId(): Int = R.layout.fragment_note

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()
        setupToolbar()
        setupNoteButton()
        setupEditNoteButton()
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
        presenter.initView(noteId)
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
        presenter.detachView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        noteInfoBundle = savedInstanceState
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            val markerId = noteTypeSpinner.selectedItemId.toInt()
            putInt(NOTE_MARKER_ID, markerId)
        }
    }

    override fun closeScreen() {
        activity?.let {
            findNavController(it, R.id.nav_host_fragment).navigateUp()
        }
    }

    override fun showMarkers(markers: List<MarkerTypeUi>) {
        val adapter = MarkerAdapter(context, R.layout.item_note_marker, markers)
        noteTypeSpinner.adapter = adapter
        restoreSelectedMarker()
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun setEditMode(isEditMode: Boolean) {
        if (isEditMode) {
            addNoteButton.makeInvisible()
            editNoteButton.makeVisible()
        } else {
            addNoteButton.makeVisible()
            editNoteButton.makeInvisible()
        }
    }

    override fun showNote(note: Note) {
        if (noteInfoBundle != null) {
            restoreSelectedMarker()
        } else {
            noteName.setText(note.name)
            noteContent.setText(note.content)
            //TODO: Think about this - using ordinal isn't good idea
            noteTypeSpinner.setSelection(note.markerType.ordinal)
        }
    }

    override fun setNoteNameEmptyError(isVisible: Boolean) {
        if (isVisible) {
            noteName.error = context?.getString(R.string.message_empty_note_name)
        } else {
            noteName.error = null
        }
    }

    private fun injectDependencies() {
        DiProvider.appComponent.inject(this)
    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            activity?.let {
                findNavController(it, R.id.nav_host_fragment).navigateUp()
            }
        }
    }

    private fun setupNoteButton() {
        addNoteButton.setOnClickListener {
            val name = noteName.text.toString()
            val content = noteContent.text.toString()
            val currentTime = System.currentTimeMillis()
            val markerType = noteTypeSpinner.selectedItem as MarkerTypeUi

            presenter.onClickNoteButton(name, content, currentTime, markerType)
        }
    }

    private fun setupEditNoteButton() {
        editNoteButton.setOnClickListener {
            val name = noteName.text.toString()
            val content = noteContent.text.toString()
            val currentTime = System.currentTimeMillis()
            val markerType = noteTypeSpinner.selectedItem as MarkerTypeUi

            presenter.onClickEditNoteButton(noteId, name, content, currentTime, markerType)
        }
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun restoreSelectedMarker() {
        val noteBundle = noteInfoBundle
        if (noteBundle != null && noteBundle.containsKey(NOTE_MARKER_ID)) {
            val markerSelection = noteBundle.getInt(NOTE_MARKER_ID)
            noteTypeSpinner.setSelection(markerSelection)
        }
    }
}
