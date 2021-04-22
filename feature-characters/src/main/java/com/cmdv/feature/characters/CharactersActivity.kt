package com.cmdv.feature.characters

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.cmdv.common.KEY_CHARACTER_ID
import com.cmdv.core.base.BaseActivity
import com.cmdv.feature.characters.databinding.ActivityCharactersBinding
import com.cmdv.feature.characters.listener.CharactersFragmentListener

class CharactersActivity :
    BaseActivity<CharactersActivity, ActivityCharactersBinding>(R.layout.activity_characters),
    CharactersFragmentListener {

    override fun initView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        NavigationUI.setupWithNavController(
            binding.bottomNav,
            navHostFragment.navController
        )
    }

    override fun observe() {
    }

    override fun onCharacterClick(characterId: Int) {
        val bundle = Bundle()
        bundle.putInt(KEY_CHARACTER_ID, characterId)
        navigator.toCharacterDetails(this, bundle, false)
    }
}