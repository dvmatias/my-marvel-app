package com.cmdv.feature.characterdetails

import android.util.Log
import android.view.View
import com.cmdv.common.KEY_CHARACTER_ID
import com.cmdv.core.base.BaseActivity
import com.cmdv.domain.model.CharacterModel
import com.cmdv.domain.utils.LiveDataStatusWrapper
import com.cmdv.feature.R
import com.cmdv.feature.databinding.ActivityCharacterDetailsBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class CharacterDetailsActivity :
    BaseActivity<CharacterDetailsActivity, ActivityCharacterDetailsBinding>(R.layout.activity_character_details) {
    val viewModel: CharacterDetailsViewModel by viewModel()

    private var characterId: Int? = null

    override fun getExtras() {
        Log.d(tag, "test")
        characterId = intent.extras?.getInt(KEY_CHARACTER_ID)
    }

    override fun initView() { }

    override fun observe() {
        viewModel.character.observe(this, {
            when (it.status) {
                LiveDataStatusWrapper.Status.LOADING -> setLoadingViewState()
                LiveDataStatusWrapper.Status.ERROR -> setErrorViewState()
                LiveDataStatusWrapper.Status.SUCCESS -> it.data?.let { character ->
                    setSuccessViewState(character)
                }
            }
        })
        characterId?.let { viewModel.getCharacter(it) }
    }

    private fun setLoadingViewState() {
        binding.loadingView.visibility = View.VISIBLE
        binding.errorView.visibility = View.GONE
    }

    private fun setErrorViewState() {
        binding.loadingView.visibility = View.GONE
        binding.errorView.visibility = View.VISIBLE
    }

    private fun setSuccessViewState(character: CharacterModel) {
        Log.d("Shit!", "onScroll load more")
        binding.loadingView.visibility = View.GONE
        binding.errorView.visibility = View.GONE
        binding.character = character
        binding.groupSuccess.visibility = View.VISIBLE
    }

}