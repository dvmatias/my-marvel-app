package com.cmdv.core.base

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.cmdv.core.navigation.Navigator
import org.koin.android.ext.android.inject

private const val TAG = "ACTIVITY :: "

abstract class BaseActivity<in A, B>(
    @LayoutRes private val layoutResId: Int?
) : AppCompatActivity()
        where A : Activity,
              B : ViewDataBinding {

    protected val tag: String = this::class.java.simpleName
    protected val navigator: Navigator by inject()
    protected lateinit var binding: B

    open fun getExtras() {}
    abstract fun initView()
    protected abstract fun observe()

    final override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initialize()
    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        layoutResId?.let { id ->
            val dataBinder = DataBindingUtil.setContentView<B>(this, id)
            onActivityCreated(dataBinder)
        }
        logActivityClassName()
        initView()
        observe()
    }

    private fun onActivityCreated(dataBinder: B) {
        binding = dataBinder
    }

    @Suppress("UNCHECKED_CAST")
    private fun logActivityClassName() {
        Log.d(TAG, (this as A)::class.java.simpleName)
    }
}