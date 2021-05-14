package com.cmdv.feature.characters.fragment

import android.graphics.drawable.AnimatedVectorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmdv.common.components.dialog.CustomDialog
import com.cmdv.core.base.BaseFragment
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.utils.LiveDataStatusWrapper.Status
import com.cmdv.feature.characters.R
import com.cmdv.feature.characters.adapter.IndexFavoriteCharacterAdapter
import com.cmdv.feature.characters.databinding.FragmentFavoritesBinding
import com.cmdv.feature.characters.itemdecorator.IndexFavoriteCharacterDecorator
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class FavoritesFragment :
    BaseFragment<FavoritesFragment, FragmentFavoritesBinding>(R.layout.fragment_favorites) {
    private val viewModel: FavoritesViewModel by viewModel()

    private lateinit var indexFavoriteCharacterAdapter: IndexFavoriteCharacterAdapter

    override fun initView() {
        setupRecycler()
        binding.favoritesToolbar.imageViewDeleteFavoritesButton.setOnClickListener {
            CustomDialog.build(requireActivity().supportFragmentManager, requireContext()) {
                ContextCompat.getDrawable(requireContext(), R.drawable.mock_character_img)
                    ?.let { setIcon(it) }
                setTitle(getString(R.string.title_delete_all_favorites_dialog))
                setMessage(getString(R.string.message_delete_all_favorites_dialog))
                setPositiveButton(getString(R.string.label_delete_all_favorites_label_positive)) { dialog, _ ->
                    dialog.dismiss()
                    viewModel.removeAll()
                }
                setNegativeButton(getString(R.string.label_delete_all_favorites_label_negative)) { dialog, _ ->
                    dialog.dismiss()
                }
            }.also {
                it.isCancelable = true
            }.show()
        }
    }

    override fun observe() {
        viewModel.favoriteCharacters.observe(this, {
            when (it.status) {
                Status.LOADING -> setLoadingViewState()
                Status.SUCCESS -> {
                    it.data?.let { favoriteCharacters ->
                        setSuccessViewState(favoriteCharacters)
                    } ?: kotlin.run { setErrorViewState() }
                }
                Status.ERROR -> setErrorViewState()
            }
        })

        viewModel.removeAll.observe(this, { event ->
            event.getContentIfNotHandled()?.let {
                viewModel.getFavoritesCharacters()
            }
        })
    }

    private fun setupRecycler() {
        indexFavoriteCharacterAdapter = IndexFavoriteCharacterAdapter()
        binding.recyclerIndexFavoriteCharacter.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = indexFavoriteCharacterAdapter
            addItemDecoration(IndexFavoriteCharacterDecorator())
        }
    }

    private fun setSuccessViewState(favoriteCharacters: List<CharacterModel>) {
        binding.progressBar.visibility = View.GONE
        binding.imageViewEmptyState.visibility = View.GONE
        if (favoriteCharacters.isEmpty()) {
            indexFavoriteCharacterAdapter.clear()
            setEmptyViewState()
            binding.favoritesToolbar.imageViewDeleteFavoritesButton.visibility = View.GONE
        } else {
            indexFavoriteCharacterAdapter.setCharacters(favoriteCharacters)
            binding.favoritesToolbar.imageViewDeleteFavoritesButton.visibility = View.VISIBLE
        }
    }

    private fun setLoadingViewState() {
        binding.favoritesToolbar.imageViewDeleteFavoritesButton.visibility = View.GONE
        binding.imageViewEmptyState.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun setEmptyViewState() {
        with(binding.imageViewEmptyState) {
            CoroutineScope(Dispatchers.Main.immediate).launch {
                setImageDrawable(null)
                visibility = View.VISIBLE
                delay(150)
                setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.animated_empty_state_spider_man
                    )
                )
                (drawable as AnimatedVectorDrawable).start()
            }
        }
    }

    private fun setErrorViewState() {

    }
}