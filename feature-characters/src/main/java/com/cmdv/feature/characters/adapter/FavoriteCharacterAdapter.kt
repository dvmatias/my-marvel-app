package com.cmdv.feature.characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmdv.common.DETACHED_TO_ROOT
import com.cmdv.domain.model.CharacterModel
import com.cmdv.feature.characters.databinding.ItemFavoriteCharacterBinding

private const val HEADER_POSITION = 0

class FavoriteCharacterAdapter :
    RecyclerView.Adapter<FavoriteCharacterAdapter.FavoriteCharacterViewHolder>() {

    private val favoriteCharacters: ArrayList<CharacterModel> = arrayListOf()
    private lateinit var inflater: LayoutInflater

    fun setItems(favoriteCharacters: List<CharacterModel>) {
        this.favoriteCharacters.addAll(favoriteCharacters)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int =
        when (position) {
            HEADER_POSITION -> ViewType.HEADER_MAIN.viewType
            else -> ViewType.CHARACTER.viewType
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteCharacterViewHolder {
        inflater = LayoutInflater.from(parent.context)
        return FavoriteCharacterViewHolder(
            ItemFavoriteCharacterBinding.inflate(inflater, parent, DETACHED_TO_ROOT)
        )
    }

    override fun onBindViewHolder(holder: FavoriteCharacterViewHolder, position: Int) {
        holder.bindItem(favoriteCharacters[position])
    }

    override fun getItemCount(): Int = this.favoriteCharacters.size

    class FavoriteCharacterViewHolder(
        private val binding: ItemFavoriteCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(character: CharacterModel) {
            binding.character = character
        }
    }

}