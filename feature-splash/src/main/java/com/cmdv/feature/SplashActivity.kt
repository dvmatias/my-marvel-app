package com.cmdv.feature

import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.cmdv.common.extensions.hideSystemUi
import com.cmdv.core.base.BaseActivity
import com.cmdv.feature.databinding.ActivitySplashBinding
import org.koin.android.viewmodel.ext.android.viewModel


class SplashActivity : BaseActivity<SplashActivity, ActivitySplashBinding>() {
    private val viewModel: SplashViewModel by viewModel()
    private lateinit var navController: NavController

    override fun getLayoutRes(): Int = R.layout.activity_splash

    override fun initView() {
        navController =
            (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment)
                .navController
        handleBackAction()
    }

    override fun observe() {
        viewModel.getDestination().observe(this, { event ->
            navigate(event.peekContent())
        })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) window.let { it.decorView.hideSystemUi(it) }
    }

    private fun navigate(destinationId: Int) = kotlin.run { navController.navigate(destinationId) }

    private fun handleBackAction() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }
}