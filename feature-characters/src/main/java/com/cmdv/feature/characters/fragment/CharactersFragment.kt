package com.cmdv.feature.characters.fragment

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cmdv.core.base.BaseFragment
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.utils.FailureType
import com.cmdv.domain.utils.LiveDataStatusWrapper
import com.cmdv.feature.characters.CharactersViewModel
import com.cmdv.feature.characters.R
import com.cmdv.feature.characters.adapter.CharacterAdapter
import com.cmdv.feature.characters.databinding.FragmentCharactersBinding
import com.cmdv.feature.characters.layoutmanager.CharacterLayoutManager
import com.cmdv.feature.characters.listener.CharacterAdapterListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel

@Suppress("EXPERIMENTAL_API_USAGE")
class CharactersFragment : BaseFragment<CharactersFragment, FragmentCharactersBinding>(R.layout.fragment_characters) {

    private val viewModel: CharactersViewModel by stateViewModel()
    private val characterAdapter: CharacterAdapter by inject()
    private lateinit var characterLayoutManager: CharacterLayoutManager

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.getFirstTimeCharacters()
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            characterAdapter.onScroll(characterLayoutManager.findLastVisibleItemPosition())
        }
    }

    private val characterAdapterListener = object : CharacterAdapterListener {
        override fun loadMore(offset: Int) {
            viewModel.getMoreCharacters(offset = offset)
        }

        override fun onCharacterClick(view: View, id: Int) {
            Toast.makeText(requireContext(), "Click ID $id", Toast.LENGTH_SHORT).show()
        }
    }

    override fun initView() {
        characterLayoutManager = CharacterLayoutManager(requireContext(), characterAdapter)
        characterAdapter.listener = characterAdapterListener
        binding.recyclerCharacter.apply {
            layoutManager = characterLayoutManager
            adapter = characterAdapter
            addOnScrollListener(scrollListener)
        }

        binding.swipeRefresh.setOnRefreshListener(onRefreshListener)
    }

    override fun observe() {
        viewModel.getFirstTimeCharacters()
        viewModel.lastFetchedCharacters.observe(this, {
            when (it.status) {
                LiveDataStatusWrapper.Status.SUCCESS -> {
                    val characters = it.data?.characters ?: listOf()
                    if (characters.isNotEmpty()) {
                        setSuccessViewState(characters)
                    } else {
                        setEmptyState()
                    }
                }
                LiveDataStatusWrapper.Status.LOADING -> setLoadingViewState()
                LiveDataStatusWrapper.Status.ERROR -> setErrorViewState(it.failureType)
            }
        })
    }

    private fun setLoadingViewState() {
        if (characterAdapter.isEmpty()) {
            binding.swipeRefresh.isRefreshing = true
            binding.recyclerCharacter.visibility = View.GONE
        } else {
            characterAdapter.isLoading = true
        }
    }

    private fun setSuccessViewState(characters: List<CharacterModel>) {
        characterAdapter.addItems(characters)
        binding.swipeRefresh.isRefreshing = false
        binding.recyclerCharacter.visibility = View.VISIBLE
    }

    private fun setEmptyState() {
        // TODO
        Log.d(tag, "Empty State")
    }

    private fun setErrorViewState(failureType: FailureType?) {
        // TODO
        Log.d(tag, "Failure State")
    }
}