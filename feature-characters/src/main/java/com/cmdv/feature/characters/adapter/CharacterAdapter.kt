package com.cmdv.feature.characters.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmdv.domain.model.CharacterModel
import com.cmdv.feature.characters.databinding.ItemCharacterBinding
import com.cmdv.feature.characters.databinding.ItemLoadingBinding
import com.cmdv.feature.characters.listener.CharacterAdapterListener

enum class ViewType(val viewType: Int) {
    CHARACTER(0),
    FOOTER_LOADING(1)
}

private const val ITEM_COUNT_BEFORE_LOAD_MORE = 6

class CharacterAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val characters: ArrayList<CharacterModel> = arrayListOf()
    internal var isLoading = true
        set(value) {
            field = value
            notifyItemChanged(charactersCount())
        }
    internal var listener: CharacterAdapterListener? = null

    fun addItems(characters: List<CharacterModel>) {
        val startIndex = itemCount
        this.characters.addAll(characters)
        isLoading = false
        notifyItemRangeChanged(startIndex, itemCount)
    }

    fun onScroll(lastVisibleItemPosition: Int) {
        if (shouldLoadMore(lastVisibleItemPosition)) listener?.loadMore(charactersCount())
    }

    fun isEmpty(): Boolean = itemCount == 1

    private fun shouldLoadMore(lastVisibleItemPosition: Int): Boolean =
        !isLoading && charactersCount() != 0 && charactersCount() <= lastVisibleItemPosition + ITEM_COUNT_BEFORE_LOAD_MORE


    override fun getItemViewType(position: Int): Int =
        when (position) {
            charactersCount() -> ViewType.FOOTER_LOADING.viewType
            else -> ViewType.CHARACTER.viewType
        }

    private fun charactersCount() = itemCount - 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ViewType.CHARACTER.viewType -> CharacterViewHolder(
                ItemCharacterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewType.FOOTER_LOADING.viewType -> LoadingViewHolder(
                ItemLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalStateException("")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ViewType.CHARACTER.viewType -> (holder as CharacterViewHolder).bindItem(characters[position], listener, position)
            ViewType.FOOTER_LOADING.viewType -> (holder as LoadingViewHolder).show(isLoading)
        }

    }

    override fun getItemCount(): Int = this.characters.size + 1

    fun updateFavourite(position: Int, isFavourite: Boolean) {
        this.characters[position].isFavourite = isFavourite
        notifyItemChanged(position)
    }

    class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(character: CharacterModel, listener: CharacterAdapterListener?, position: Int) {
            binding.character = character
            binding.listener = listener
            binding.position = position
        }
    }

    class LoadingViewHolder(private val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun show(show: Boolean) {
            binding.root.visibility = if (show) View.VISIBLE else View.GONE
        }
    }
}