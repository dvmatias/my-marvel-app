package com.cmdv.feature.characters.fragment

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cmdv.core.base.BaseFragment
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.utils.FailureType
import com.cmdv.domain.utils.LiveDataStatusWrapper
import com.cmdv.feature.characters.R
import com.cmdv.feature.characters.adapter.CharacterAdapter
import com.cmdv.feature.characters.databinding.FragmentCharactersBinding
import com.cmdv.feature.characters.layoutmanager.CharacterLayoutManager
import com.cmdv.feature.characters.listener.CharacterAdapterListener
import com.cmdv.feature.characters.listener.CharactersFragmentListener
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
@Suppress("EXPERIMENTAL_API_USAGE")
class CharactersFragment :
    BaseFragment<CharactersFragment, FragmentCharactersBinding>(R.layout.fragment_characters) {
    private val viewModel: CharactersViewModel by viewModel()
    private val characterAdapter: CharacterAdapter by inject()
    private lateinit var characterLayoutManager: CharacterLayoutManager
    private var fragmentListener: CharactersFragmentListener? = null

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.getCharacters(fetch = false)
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            characterAdapter.onScroll(characterLayoutManager.findLastVisibleItemPosition())
        }
    }

    private val characterAdapterListener = object : CharacterAdapterListener {
        override fun onLoadMoreCharacters(offset: Int) {
            Log.d("Shit!", "onLoadMoreCharacters(offset: $offset)")
            viewModel.getCharacters(fetch = true, offset = offset)
        }

        override fun onCharacterClick(characterId: Int) {
            fragmentListener?.onCharacterClick(characterId)
        }

        override fun onFavoriteClick(characterId: Int, characterIndex: Int, isFavourite: Boolean) {
            Log.d("Shit!", "onFavoriteClick(characterIndex: $characterIndex, isFavourite: $isFavourite)")
            when (isFavourite) {
                true -> viewModel.addFavorite(characterId, characterIndex)
                false -> viewModel.removeFavorite(characterId, characterIndex)
            }
        }
    }

    override fun initView() {
        if (activity is CharactersFragmentListener) {
            this.fragmentListener = activity as CharactersFragmentListener
        }
        setupRecycler()
        setupSwipeRefresh()
        setLoadingViewState()
    }

    override fun observe() {
        viewModel.init()

        viewModel.characters.origianl.observe(this, {
            when (it.status) {
                LiveDataStatusWrapper.Status.SUCCESS -> {
                    (it.data ?: listOf()).let { characters ->
                        if (characters.isNotEmpty()) {
                            setSuccessViewState(characters)
                        } else {
                            setEmptyViewState()
                        }
                    }
                }
                LiveDataStatusWrapper.Status.LOADING -> setLoadingViewState()
                LiveDataStatusWrapper.Status.ERROR -> setErrorViewState(it.failureType)
            }
        })

        viewModel.addedFavoritePosition.observe(this, { event ->
            event.getContentIfNotHandled()?.let { position ->
                handleFav(position, true)
            }
        })

        viewModel.removedFavoritePosition.observe(this, { event ->
            event.getContentIfNotHandled()?.let { position ->
                handleFav(position, false)
            }
        })
    }

    private fun setupRecycler() {
        characterLayoutManager = CharacterLayoutManager(requireContext(), characterAdapter)
        characterAdapter.listener = characterAdapterListener
        binding.recyclerCharacter.apply {
            layoutManager = characterLayoutManager
            adapter = characterAdapter
            addOnScrollListener(scrollListener)
            itemAnimator = null
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener(onRefreshListener)
    }

    private fun handleFav(updatedPosition: Int, isFavorite: Boolean) {
        characterAdapter.updateFavourite(updatedPosition, isFavorite)
    }

    private fun setLoadingViewState() {
        if (characterAdapter.isEmpty()) {
            binding.swipeRefresh.isRefreshing = true
            binding.recyclerCharacter.visibility = View.GONE
            characterAdapter.isLoading = false
        } else {
            characterAdapter.isLoading = true
        }
    }

    private fun setSuccessViewState(characters: List<CharacterModel>) {
        characterAdapter.addItems(characters)
        binding.swipeRefresh.isRefreshing = false
        characterAdapter.isLoading = false
        binding.recyclerCharacter.visibility = View.VISIBLE
    }

    private fun setEmptyViewState() {
        // TODO
        Log.d(tag, "Empty State")
    }

    private fun setErrorViewState(failureType: FailureType?) {
        // TODO
        Log.d(tag, "Failure State")
    }
}