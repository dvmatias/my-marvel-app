package com.cmdv.core.base

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.cmdv.core.navigation.Navigator
import org.koin.android.ext.android.inject

abstract class BaseActivity<in A : Activity, B : ViewDataBinding> : AppCompatActivity() {
    protected val navigator: Navigator by inject()
    protected lateinit var binding: B

    protected open fun getExtras() {}
    protected abstract fun getLayoutRes(): Int
    protected abstract fun initView()
    protected abstract fun observe()

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        binding.lifecycleOwner = this

        logActivityClassName((this as A)::class.java.simpleName)
        initView()
        observe()
    }

    private fun logActivityClassName(activityClassName: String) {
        Log.d("SCREEN :: ", activityClassName)
    }
}