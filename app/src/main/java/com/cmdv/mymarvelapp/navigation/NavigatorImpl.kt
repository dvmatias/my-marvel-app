package com.cmdv.mymarvelapp.navigation

import android.app.Activity
import android.os.Bundle
import com.cmdv.common.extensions.navigateTo
import com.cmdv.core.navigation.Navigator
import com.cmdv.feature.characterdetails.CharacterDetailsActivity
import com.cmdv.feature.characters.CharactersActivity

class NavigatorImpl : Navigator {
    override fun toCharacters(from: Activity, data: Bundle?, finish: Boolean) {
        from.navigateTo<CharactersActivity>(data, finish)
    }

    override fun toCharacterDetails(from: Activity, data: Bundle?, finish: Boolean) {
        from.navigateTo<CharacterDetailsActivity>(data, finish)
    }
}