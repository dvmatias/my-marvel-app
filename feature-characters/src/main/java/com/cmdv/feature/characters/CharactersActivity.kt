package com.cmdv.feature.characters

import com.cmdv.core.base.BaseActivity
import com.cmdv.domain.utils.LiveDataStatusWrapper
import com.cmdv.feature.characters.databinding.ActivityCharactersBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class CharactersActivity :
    BaseActivity<CharactersActivity, ActivityCharactersBinding>(R.layout.activity_characters) {
    private val viewModel: CharactersViewModel by viewModel()

    override fun initView() {
        // TODO
    }

    override fun observe() {
        viewModel.characters.observe(this, {
            when (it.status) {
                LiveDataStatusWrapper.Status.SUCCESS -> {
                    // TODO
                }
                LiveDataStatusWrapper.Status.LOADING -> {
                    // TODO
                }
                LiveDataStatusWrapper.Status.ERROR -> {
                    // TODO
                }
            }
        })
    }

}