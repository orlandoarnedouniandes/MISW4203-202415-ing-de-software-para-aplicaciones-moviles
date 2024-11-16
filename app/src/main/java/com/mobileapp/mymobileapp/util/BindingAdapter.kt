package com.mobileapp.mymobileapp.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    url?.let {
        Picasso.get().load(it).into(view)
    }
}

@BindingAdapter("year")
fun extractYear(view: TextView, date: String?) {
    date?.let {
        view.text = DateUtils.extractYear(it)
    }
}