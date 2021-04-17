package com.cmdv.feature.splash

import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.cmdv.common.extensions.hideSystemUi
import com.cmdv.core.base.BaseActivity
import com.cmdv.feature.splash.databinding.ActivitySplashBinding
import org.koin.androidx.viewmodel.ext.android.stateViewModel


class SplashActivity : BaseActivity<SplashActivity, ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel: SplashViewModel by stateViewModel()
    private lateinit var navController: NavController

    override fun initView() {
        navController =
            (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment)
                .navController
        handleBackAction()
    }

    override fun observe() {
        viewModel.newDestination.observe(this, { event ->
            navigate(event.peekContent())
        })

        viewModel.goToCharactersActivity.observe(this, { event ->
            if (event.peekContent()) goToCharactersActivity()
        })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) window.let { it.decorView.hideSystemUi(it) }
    }

    private fun navigate(destinationId: Int) {
        navController.navigate(destinationId)
    }

    private fun goToCharactersActivity() {
        navigator.toCharacters(from = this, finish = true)
    }

    private fun handleBackAction() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }
}