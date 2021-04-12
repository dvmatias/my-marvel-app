package com.cmdv.feature.fragments

import androidx.lifecycle.ViewModelProviders
import com.cmdv.core.base.BaseFragment
import com.cmdv.feature.R
import com.cmdv.feature.SplashViewModel
import com.cmdv.feature.databinding.FragmentAppWelcomeBinding

class AppWelcomeFragment : BaseFragment<AppWelcomeFragment, FragmentAppWelcomeBinding>() {

    override fun getLayoutRes(): Int = R.layout.fragment_app_welcome

    override fun initView() {
        binding.viewModel = ViewModelProviders.of(requireActivity()).get(SplashViewModel::class.java)
        binding.navDirection = AppWelcomeFragmentDirections.actionWelcomeFragmentToAppDetailsFragment()
    }

}