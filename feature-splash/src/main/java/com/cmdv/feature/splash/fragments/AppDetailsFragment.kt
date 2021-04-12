package com.cmdv.feature.splash.fragments

import androidx.lifecycle.ViewModelProviders
import com.cmdv.core.base.BaseFragment
import com.cmdv.feature.splash.R
import com.cmdv.feature.splash.SplashViewModel
import com.cmdv.feature.splash.databinding.FragmentAppDetailsBinding

class AppDetailsFragment : BaseFragment<AppDetailsFragment, FragmentAppDetailsBinding>() {

    override fun getLayoutRes(): Int = R.layout.fragment_app_details

    override fun initView() {
        binding.viewModel = ViewModelProviders.of(requireActivity()).get(SplashViewModel::class.java)
    }
}