package com.cmdv.core.utlis

import android.widget.ImageView
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
        .centerCrop()
        .into(imageView)
}