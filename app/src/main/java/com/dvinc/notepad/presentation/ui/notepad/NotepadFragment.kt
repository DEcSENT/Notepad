/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.notepad

import android.app.AlertDialog
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.dvinc.notepad.NotepadApplication
import com.dvinc.notepad.R
import com.dvinc.notepad.presentation.ui.base.BaseFragment
import javax.inject.Inject
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.navigation.Navigation.findNavController
import com.dvinc.notepad.common.extension.makeGone
import com.dvinc.notepad.common.extension.makeVisible
import com.dvinc.notepad.common.extension.toggleGone
import com.dvinc.notepad.presentation.adapter.item.NoteItem
import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.dvinc.notepad.presentation.model.NoteUi
import com.dvinc.notepad.presentation.ui.filter.FilterClickListener
import com.dvinc.notepad.presentation.ui.filter.FilterDialogFragment
import com.dvinc.notepad.presentation.ui.note.NoteFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_filter_icon as filterIcon
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_small_filter_icon as filterSmallIcon
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_recycler as notesRecycler
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_fab as fab
import kotlinx.android.synthetic.main.fragment_notepad.fragment_notepad_empty_view as emptyView

class NotepadFragment : BaseFragment(), NotepadView, FilterClickListener {

    companion object {
        private const val KEY_CURRENT_MARKER_FILTER = "keyCurrentMarkerFilter"
    }

    @Inject
    lateinit var notePadPresenter: NotepadPresenter

    private val noteAdapter: GroupAdapter<ViewHolder> = GroupAdapter()

    override fun getFragmentLayoutId(): Int = R.layout.fragment_notepad

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectPresenter()
        setupNoteRecycler()
        setupFabButton()
        setupSwipeToDelete()
        setupNotesAdapterClickListener()
        setupFilterButton()
    }

    override fun onResume() {
        super.onResume()
        notePadPresenter.attachView(this)
        val selectedMarkerType = arguments?.getSerializable(KEY_CURRENT_MARKER_FILTER) as? MarkerTypeUi
        notePadPresenter.initNotes(selectedMarkerType)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        notePadPresenter.detachView()
    }

    override fun showNotes(notes: List<NoteUi>) {
        noteAdapter.clear()
        noteAdapter.addAll(notes.map {
            NoteItem(it)
        })
    }

    override fun setEmptyView(isVisible: Boolean) = emptyView.toggleGone(isVisible)

    override fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showDeleteNoteDialog(notePosition: Int, swipedItemPosition: Int) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle(R.string.dialog_delete_note_header)
                .setPositiveButton(R.string.ok) { _, _ ->
                    notePadPresenter.deleteNote(notePosition)
                }
                .setNegativeButton(R.string.no) { dialog, _ ->
                    noteAdapter.notifyItemChanged(swipedItemPosition)
                    dialog.cancel()
                }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    override fun storeCurrentSelectedMarkerType(type: MarkerTypeUi?) {
        arguments?.putSerializable(KEY_CURRENT_MARKER_FILTER, type)
    }

    override fun clearStoredCurrentSelectedMarkerType() {
        arguments?.putSerializable(KEY_CURRENT_MARKER_FILTER, null)
    }

    override fun loadAllNotes() {
        notePadPresenter.loadAllNotes()
    }

    override fun loadNotesBySpecificMarkerType(type: MarkerTypeUi) {
        notePadPresenter.filterNotes(type)
    }

    override fun showCurrentFilterIcon(markerTypeUi: MarkerTypeUi) {
        filterSmallIcon.makeVisible()
        context?.let {
            filterSmallIcon.drawable.mutate().setColorFilter(
                    ContextCompat.getColor(it, markerTypeUi.markerColor),
                    PorterDuff.Mode.MULTIPLY)
        }
    }

    override fun hideCurrentFilterIcon() {
        filterSmallIcon.makeGone()
    }

    override fun goToNoteScreen(noteId: Long) {
        val bundle = Bundle().apply { putLong(NoteFragment.NOTE_ID, noteId) }
        activity?.let {
            findNavController(it, R.id.nav_host_fragment)
                    .navigate(R.id.action_notepadFragment_to_noteFragment, bundle)
        }
    }

    private fun injectPresenter() {
        (context?.applicationContext as NotepadApplication).appComponent.inject(this)
    }

    private fun setupNoteRecycler() {
        notesRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        notesRecycler.adapter = noteAdapter
    }

    private fun setupFabButton() {
        fab.setOnClickListener {
            activity?.let {
                findNavController(it, R.id.nav_host_fragment)
                        .navigate(R.id.action_notepadFragment_to_noteFragment)
            }
        }

        //Hiding fab by scroll
        notesRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0)
                    fab.hide()
                else if (dy < 0)
                    fab.show()
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
                val item = noteAdapter.getItem(position)
                if (item is NoteItem) {
                    val swipedNoteId = item.note.id.toInt()
                    notePadPresenter.onNoteSwiped(swipedNoteId, viewHolder.adapterPosition)
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(notesRecycler)
    }

    private fun setupNotesAdapterClickListener() {
        noteAdapter.setOnItemClickListener { item, _ ->
            if (item is NoteItem) {
                notePadPresenter.onNoteItemClick(item.note)
            }
        }
    }

    private fun setupFilterButton() {
        filterIcon.setOnClickListener {
            val dialog = FilterDialogFragment.newInstance()
            dialog.setTargetFragment(this, 0)
            dialog.show(fragmentManager, FilterDialogFragment.TAG)
        }
    }
}
