package com.cmdv.common.components.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.cmdv.common.R
import com.cmdv.common.databinding.ViewCustomButtonBinding

enum class ButtonType {
    PRIMARY,
    SECONDARY
}

class CustomButtonView : ConstraintLayout {

    private val binding: ViewCustomButtonBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_custom_button,
            this,
            false
        )

    constructor(context: Context) : super(context) {
        initView(context, null, null)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView(context, attributeSet, null)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        initView(context, attributeSet, defStyleAttr)
    }

    init {
        addView(binding.root)
    }

    private fun initView(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int?) {

    }

    fun setType(buttonType: ButtonType) {

            when (buttonType) {
                ButtonType.PRIMARY -> {
                    binding.button.setBackgroundResource(R.drawable.selector_custom_button_primary_background)
                    binding.button.setTextColor(ContextCompat.getColorStateList(context, R.color.selector_button_view_custom_primary_text))
                }
                ButtonType.SECONDARY -> {
                    binding.button.setBackgroundResource(R.drawable.selector_custom_button_secondary_background)
                    binding.button.setTextColor(ContextCompat.getColorStateList(context, R.color.selector_button_view_custom_secondary_text))
                }
            }

    }

    fun setLabel(label: String) {
        binding.button.text = label
    }

    fun setClickListener(listener: OnClickListener) {
        binding.button.setOnClickListener { listener.onClick(it) }
    }

}