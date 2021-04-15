package com.cmdv.feature.characters.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.cmdv.feature.characters.adapter.CharacterAdapter

private const val SPAN_COUNT_CHARACTER = 3
private const val SPAN_COUNT_LOADING = 1

class CharacterLayoutManager(context: Context, adapter: CharacterAdapter) :
    GridLayoutManager(context, SPAN_COUNT_CHARACTER) {

    init {
        this.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position == (adapter.itemCount - 1)) {
                    true -> SPAN_COUNT_CHARACTER
                    false -> SPAN_COUNT_LOADING
                }
            }
        }
    }

}