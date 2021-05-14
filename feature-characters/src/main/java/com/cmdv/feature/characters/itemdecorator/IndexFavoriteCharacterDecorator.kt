package com.cmdv.feature.characters.itemdecorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cmdv.core.utlis.DimensHelper

class IndexFavoriteCharacterDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = DimensHelper.dpToPx(view.context, 36F).toInt()
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.set(0, outRect.top, 0, 0)
        } else {
            outRect.set(0, 0, 0, 0)
        }
    }

}