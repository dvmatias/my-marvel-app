package com.cmdv.feature.characters

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.cmdv.core.base.BaseActivity
import com.cmdv.feature.characters.databinding.ActivityCharactersBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel

@ExperimentalCoroutinesApi
class CharactersActivity : BaseActivity<CharactersActivity, ActivityCharactersBinding>(R.layout.activity_characters) {
    private val viewModel: CharactersViewModel by stateViewModel()

    override fun initView() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        NavigationUI.setupWithNavController(
            binding.bottomNav,
            navHostFragment.navController
        )
    }

    override fun observe() {

    }

}