package com.cmdv.feature.splash.fragment

import androidx.lifecycle.ViewModelProviders
import com.cmdv.core.base.BaseFragment
import com.cmdv.feature.splash.R
import com.cmdv.feature.splash.SplashViewModel
import com.cmdv.feature.splash.databinding.FragmentAppWelcomeBinding

class AppWelcomeFragment : BaseFragment<AppWelcomeFragment, FragmentAppWelcomeBinding>(R.layout.fragment_app_welcome) {

    override fun initView() {
        binding.viewModel = ViewModelProviders.of(requireActivity()).get(SplashViewModel::class.java)
        binding.navDirection =
            AppWelcomeFragmentDirections.actionAppWelcomeFragmentToAppDetailsFragment()
    }

}