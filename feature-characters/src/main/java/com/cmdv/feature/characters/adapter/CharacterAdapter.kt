package com.cmdv.feature.characters.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmdv.common.DETACHED_TO_ROOT
import com.cmdv.core.extensions.addAllNoRepeated
import com.cmdv.domain.model.CharacterModel
import com.cmdv.feature.characters.databinding.ItemCharacterBinding
import com.cmdv.feature.characters.databinding.ItemCharacterHeaderMainBinding
import com.cmdv.feature.characters.databinding.ItemLoadingBinding
import com.cmdv.feature.characters.listener.CharacterAdapterListener

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
        this.characters.addAllNoRepeated(characters)
        isLoading = false
        notifyItemRangeChanged(startIndex, itemCount)
    }

    fun onScroll(lastVisibleItemPosition: Int) {
        if (shouldLoadMore(lastVisibleItemPosition) && !isLoading) {
            isLoading = true
            listener?.onLoadMoreCharacters(charactersCount())
        }
    }

    fun isEmpty(): Boolean = itemCount == 2

    private fun shouldLoadMore(lastVisibleItemPosition: Int): Boolean =
        !isLoading && charactersCount() != 0 && charactersCount() <= lastVisibleItemPosition + ITEM_COUNT_BEFORE_LOAD_MORE


    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> ViewType.HEADER_MAIN.viewType
            itemCount - 1 -> ViewType.FOOTER_LOADING.viewType
            else -> ViewType.CHARACTER.viewType
        }

    private fun charactersCount() = itemCount - 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ViewType.HEADER_MAIN.viewType -> HeaderViewHolder(
                ItemCharacterHeaderMainBinding.inflate(LayoutInflater.from(parent.context), parent, DETACHED_TO_ROOT)
            )
            ViewType.CHARACTER.viewType -> CharacterViewHolder(
                ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, DETACHED_TO_ROOT)
            )
            ViewType.FOOTER_LOADING.viewType -> LoadingViewHolder(
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, DETACHED_TO_ROOT)
            )
            else -> throw IllegalStateException("")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ViewType.HEADER_MAIN.viewType -> (holder as HeaderViewHolder)
            ViewType.CHARACTER.viewType -> {
                val characterIndex = position - 1
                (holder as CharacterViewHolder).bindItem(characters[characterIndex], listener, characterIndex)
            }
            ViewType.FOOTER_LOADING.viewType -> (holder as LoadingViewHolder).show(isLoading)
        }

    }

    override fun getItemCount(): Int = this.characters.size + 2

    fun updateFavourite(position: Int, isFavourite: Boolean) {
        this.characters[position].isFavourite = isFavourite
        notifyItemChanged(position + 1)
    }

    class HeaderViewHolder(val binding: ItemCharacterHeaderMainBinding) : RecyclerView.ViewHolder(binding.root)

    class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(character: CharacterModel, listener: CharacterAdapterListener?, characterIndex: Int) {
            binding.character = character
            binding.listener = listener
            binding.characterIndex = characterIndex
        }
    }

    class LoadingViewHolder(private val binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun show(show: Boolean) {
            binding.root.visibility = if (show) View.VISIBLE else View.GONE
        }
    }
}