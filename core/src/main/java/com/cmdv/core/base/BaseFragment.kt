package com.cmdv.core.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<in F, B>(
    @LayoutRes private val layoutResId: Int
) : Fragment() where F : Fragment, B : ViewDataBinding {
    protected lateinit var binding: B

    protected abstract fun initView()
    open fun observe() {}

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = this

        logFragmentClassName((this as F)::class.java.simpleName)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        observe()
    }

    private fun logFragmentClassName(fragmentClassName: String) {
        Log.d("  ->  SCREEN :: ", fragmentClassName)
    }
}