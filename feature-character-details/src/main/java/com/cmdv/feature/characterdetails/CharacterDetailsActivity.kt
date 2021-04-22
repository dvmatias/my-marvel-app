package com.cmdv.feature.characterdetails

import android.util.Log
import com.cmdv.common.KEY_CHARACTER_ID
import com.cmdv.core.base.BaseActivity
import com.cmdv.feature.R
import com.cmdv.feature.databinding.ActivityCharacterDetailsBinding

class CharacterDetailsActivity :
    BaseActivity<CharacterDetailsActivity, ActivityCharacterDetailsBinding>(R.layout.activity_character_details) {

    private var characterId: Int? = null

    override fun getExtras() {
        Log.d(tag, "test")
        characterId = intent.extras?.getInt(KEY_CHARACTER_ID)
    }

    override fun initView() {
        setSupportActionBar(binding.toolbar)
    }

    override fun observe() {
        // TODO()
    }


}