/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.notepad

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.dvinc.notepad.App
import com.dvinc.notepad.R
import com.dvinc.notepad.ui.base.BaseFragment
import com.dvinc.notepad.ui.note.NoteFragment
import javax.inject.Inject
import android.support.v7.widget.helper.ItemTouchHelper
import com.dvinc.notepad.common.visible
import com.dvinc.notepad.domain.model.Note
import com.dvinc.notepad.ui.main.MainActivity
import com.dvinc.notepad.ui.adapters.NotesAdapter
import kotlinx.android.synthetic.main.fragment_notepad.*

class NotepadFragment : BaseFragment(), NotepadView {

    @Inject lateinit var notePadPresenter: NotepadPresenter

    private val notesAdapter: NotesAdapter = NotesAdapter()

    companion object {
        val TAG = "NotepadFragment"
    }

    override fun getFragmentLayoutId(): Int = R.layout.fragment_notepad

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvNotepad.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        rvNotepad.adapter = notesAdapter

        (context?.applicationContext as App).appComponent.inject(this)

        setupFabButton()
        setupSwipeToDelete()
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

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showNotes(notes: List<Note>) {
        notesAdapter.setNotes(notes)
    }

    override fun setEmptyView(isVisible: Boolean) = emptyView.visible(isVisible)

    override fun showDeletedNoteMessage() {
        Toast.makeText(context, R.string.note_deleted, Toast.LENGTH_LONG).show()
    }

    override fun showDeleteNoteDialog(notePosition: Int, swipedItemPosition: Int) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle(R.string.dialog_delete_note_header)
                .setPositiveButton(R.string.ok) { _, _ ->
                    notePadPresenter.deleteNote(notePosition)
                }
                .setNegativeButton(R.string.no, { dialog, _ ->
                    notesAdapter.notifyItemChanged(swipedItemPosition)
                    dialog.cancel()
                })

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun setupFabButton() {
        fabNewNote.setOnClickListener {
            val noteFragment = NoteFragment()
            //TODO: Think about good navigation
            (activity as MainActivity).showAndAddFragment(noteFragment, TAG)
        }

        //Hiding fab by scroll
        rvNotepad.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0)
                    fabNewNote.hide()
                else if (dy < 0)
                    fabNewNote.show()
            }
        })
    }

    private fun setupSwipeToDelete() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                val swipedNoteId = notesAdapter.getNote(position)?.id?.toInt()
                if (swipedNoteId != null) notePadPresenter.onNoteSwiped(swipedNoteId, viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rvNotepad)
    }
}
