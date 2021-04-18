package com.cmdv.core.utlis

import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.cmdv.common.extensions.secureUrl
import com.cmdv.core.R

@BindingAdapter("image")
fun bindCharacterItemImage(imageView: ImageView, imageUrl: String) {
    Glide.with(imageView.context)
        .load(imageUrl.secureUrl())
        .placeholder(R.drawable.img_character_placeholder)
        .dontAnimate()
        .let { request ->
            imageView.drawable?.let {
                request.placeholder(imageView.drawable.constantState?.newDrawable()?.mutate())
            } ?: run { request }
        }
        .centerCrop()
        .into(imageView)
}

@BindingAdapter("characterItemDescription")
fun bindCharacterItemImage(textView: TextView, description: String) {
    if (description.isBlank()) {
        textView.apply {
            text = textView.context.getString(R.string.text_item_character_no_description)
            alpha = 0.7F
            setTypeface(textView.typeface, Typeface.ITALIC);
        }
    } else {
        textView.text = description
    }
}