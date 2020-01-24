package com.dvinc.notepad.common.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dvinc.notepad.common.extension.toPx

class SpaceItemDecorator(
    private val bottomPadding: Int = 8,
    private val leftPadding: Int = 8,
    private val rightPadding: Int = 8
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            bottom = bottomPadding.toPx()
            left = leftPadding.toPx()
            right = rightPadding.toPx()
        }
    }
}
