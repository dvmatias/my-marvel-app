package com.cmdv.core.navigation

import android.app.Activity
import android.os.Bundle

interface Navigator {
    fun toCharacters(from: Activity, data: Bundle? = null, finish: Boolean? = true)
    fun toCharacterDetails(from: Activity, data: Bundle? = null, finish: Boolean? = false)
}