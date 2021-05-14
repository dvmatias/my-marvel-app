package com.cmdv.common.components.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.cmdv.common.R
import com.cmdv.common.components.button.ButtonType
import com.cmdv.common.databinding.FragmentCustomDialogBinding

class CustomDialog(private val builder: Builder) : DialogFragment() {

    private lateinit var binding: FragmentCustomDialogBinding

    private var icon: Drawable? = null
    private var title: String? = null
    private var message: String? = null
    private var buttonPositiveLabel: String? = null
    private var buttonNegativeLabel: String? = null
    private var positiveClickListener: DialogInterface.OnClickListener? = null
    private var negativeClickListener: DialogInterface.OnClickListener? = null

    init {
        this.icon = builder.icon
        this.title = builder.title
        this.message = builder.message
        this.buttonPositiveLabel = builder.buttonPositiveLabel
        this.buttonNegativeLabel = builder.buttonNegativeLabel
        this.positiveClickListener = builder.positiveClickListener
        this.negativeClickListener = builder.negativeClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_round_corner)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_custom_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()

        @Suppress("UNUSED_VARIABLE")
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setupView() {
        binding.imageViewDialogImage.apply {
            icon?.let { this.setImageDrawable(it) }
        }
        binding.textViewDialogTitle.apply {
            title?.let { this.text = it } ?: kotlin.run { this.visibility = View.GONE }
        }
        binding.textViewDialogMessage.apply {
            message?.let { this.text = it } ?: kotlin.run { this.visibility = View.GONE }
        }
        binding.buttonPositive.apply {
            buttonPositiveLabel?.let {
                setLabel(it)
                setType(ButtonType.PRIMARY)
                positiveClickListener?.let { listener ->
                    setClickListener {
                        listener.onClick(dialog, 0)
                    }
                }
            } ?: kotlin.run {
                this.visibility = View.GONE
            }
        }
        binding.buttonNegative.apply {
            buttonNegativeLabel?.let {
                setLabel(it)
                setType(ButtonType.SECONDARY)
                negativeClickListener?.let { listener ->
                    setClickListener {
                        listener.onClick(dialog, 1)
                    }
                }
            } ?: kotlin.run {
                this.visibility = View.GONE
            }
        }
    }

    fun show() {
        show(builder.fragmentManager, null)
    }

    companion object {
        inline fun build(
            fragmentManager: FragmentManager,
            context: Context,
            block: Builder.() -> Unit
        ) =
            Builder(fragmentManager, context).apply(block).build()
    }

    data class Builder(
        val fragmentManager: FragmentManager,
        val context: Context
    ) {
        var icon: Drawable? = null
            private set
        var title: String? = null
            private set
        var message: String? = null
            private set
        var buttonPositiveLabel: String? = null
            private set
        var buttonNegativeLabel: String? = null
            private set
        var positiveClickListener: DialogInterface.OnClickListener? = null
            private set
        var negativeClickListener: DialogInterface.OnClickListener? = null
            private set

        fun setIcon(icon: Drawable) = apply { this.icon = icon }
        fun setTitle(title: String) = apply { this.title = title }
        fun setMessage(message: String) = apply { this.message = message }
        fun setPositiveButton(label: String, listener: DialogInterface.OnClickListener) =
            apply {
                this.buttonPositiveLabel = label
                this.positiveClickListener = listener
            }

        fun setNegativeButton(label: String, listener: DialogInterface.OnClickListener) =
            apply {
                this.buttonNegativeLabel = label
                this.negativeClickListener = listener
            }

        fun build() = CustomDialog(this)
    }

}