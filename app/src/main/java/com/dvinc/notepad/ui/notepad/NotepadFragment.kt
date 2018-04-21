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
import butterknife.OnClick
import com.dvinc.notepad.App
import com.dvinc.notepad.R
import com.dvinc.notepad.data.database.entity.Note
import com.dvinc.notepad.ui.base.BaseFragment
import com.dvinc.notepad.ui.newnote.NewNoteFragment
import javax.inject.Inject
import android.support.v7.widget.helper.ItemTouchHelper
import com.dvinc.notepad.ui.base.RecyclerViewEmpty

class NotepadFragment : BaseFragment(), NotepadView {

    @BindView(R.id.rv_notepad) lateinit var rvNotes: RecyclerViewEmpty

    @Inject lateinit var notePadPresenter: NotepadPresenter

    private val notesAdapter: NotesAdapter = NotesAdapter()

    companion object {
        val TAG = "NotepadFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvNotes.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        rvNotes.adapter = notesAdapter
        rvNotes.setEmptyView(view.findViewById(R.id.empty_view))

        (context?.applicationContext as App).appComponent.inject(this)

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

    override fun getFragmentLayoutId(): Int {
        return R.layout.fragment_notepad
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showNotes(notes: List<Note>) {
        notesAdapter.setNotes(notes)
    }

    override fun showDeletedNoteMessage() {
        Toast.makeText(context, R.string.note_deleted, Toast.LENGTH_LONG).show()
    }

    @OnClick(R.id.fab_new_note)
    fun onFabClick(view: View) {
        val newNote = NewNoteFragment()
        newNote.show(fragmentManager, NewNoteFragment.TAG)
    }

    private fun setupSwipeToDelete() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                val deletedNoteId = notesAdapter.getNote(position)?.id?.toInt()
                if (deletedNoteId != null) {
                    notePadPresenter.deleteNote(deletedNoteId)
                    notesAdapter.notifyItemChanged(position)
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rvNotes)
    }
}
