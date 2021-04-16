package com.cmdv.feature.characters

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.cmdv.core.base.BaseActivity
import com.cmdv.feature.characters.databinding.ActivityCharactersBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class CharactersActivity :
    BaseActivity<CharactersActivity, ActivityCharactersBinding>(R.layout.activity_characters) {


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