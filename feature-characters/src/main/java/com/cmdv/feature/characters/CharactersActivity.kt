package com.cmdv.feature.characters

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.cmdv.core.base.BaseActivity
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.utils.FailureType
import com.cmdv.domain.utils.LiveDataStatusWrapper
import com.cmdv.feature.characters.adapter.CharacterAdapter
import com.cmdv.feature.characters.databinding.ActivityCharactersBinding
import com.cmdv.feature.characters.layoutmanager.CharacterLayoutManager
import com.cmdv.feature.characters.listener.CharacterAdapterListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class CharactersActivity :
    BaseActivity<CharactersActivity, ActivityCharactersBinding>(R.layout.activity_characters) {
    private val viewModel: CharactersViewModel by viewModel()
    private val characterAdapter: CharacterAdapter by inject()
    private lateinit var characterLayoutManager: CharacterLayoutManager

    private val scrollListener = object : OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            characterAdapter.onScroll(characterLayoutManager.findLastVisibleItemPosition())
        }
    }

    private val characterAdapterListener = object : CharacterAdapterListener {
        override fun loadMore(offset: Int) {
            viewModel.getCharacters(offset = offset)
        }

        override fun onCharacterClick(view: View, id: Int) {
            Toast.makeText(this@CharactersActivity, "Click ID $id", Toast.LENGTH_SHORT).show()
        }
    }

    override fun initView() {
        characterLayoutManager = CharacterLayoutManager(this, characterAdapter)
        characterAdapter.listener = characterAdapterListener
        binding.recyclerCharacter.apply {
            layoutManager = characterLayoutManager
            adapter = characterAdapter
            addOnScrollListener(scrollListener)
        }
    }

    override fun observe() {
        viewModel.characters.observe(this, {
            when (it.status) {
                LiveDataStatusWrapper.Status.SUCCESS -> {
                    val characters = it.data?.data?.results ?: listOf()
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