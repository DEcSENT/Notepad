/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.adapter.notepad

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dvinc.notepad.presentation.model.NoteUi

class NotepadSwipeToDeleteCallback(
    private val notesAdapter: NotepadAdapter,
    private val onItemSwipedListener: (note: NoteUi) -> Unit
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val deletedItemBackground = ColorDrawable(Color.RED)

    private val backgroundCornerOffset = 100

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val swipedNote = notesAdapter.getItem(position)
        onItemSwipedListener.invoke(swipedNote)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        when {
            dX > 0 -> deletedItemBackground.setBounds(
                itemView.left,
                itemView.top,
                itemView.left + (dX.toInt() + backgroundCornerOffset),
                itemView.bottom
            )
            dX < 0 -> deletedItemBackground.setBounds(
                itemView.right + (dX.toInt() - backgroundCornerOffset),
                itemView.top,
                itemView.right,
                itemView.bottom
            )
            else -> deletedItemBackground.setBounds(0, 0, 0, 0)
        }
        deletedItemBackground.draw(c)
    }
}
