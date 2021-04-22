package com.cmdv.feature.characters.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.cmdv.feature.characters.adapter.CharacterAdapter

private const val SPAN_COUNT_CHARACTER = 4
private const val SPAN_COUNT_HEADER = 1
private const val POSITION_HEADER = 0

class CharacterLayoutManager(context: Context, adapter: CharacterAdapter) :
    GridLayoutManager(context, SPAN_COUNT_CHARACTER) {

    init {
        this.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position == (adapter.itemCount - 1) || position == POSITION_HEADER) {
                    true -> SPAN_COUNT_CHARACTER
                    false -> SPAN_COUNT_HEADER
                }
            }
        }
    }

}