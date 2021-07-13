package com.dr.drhomework.util.bindingAdapter

import android.view.View
import androidx.databinding.BindingAdapter



@BindingAdapter("isVisible")
fun View.isVisible(
    isVisibility: Boolean = false) {
    println("bindMainContentAdapterData 008")
    this.visibility = if (isVisibility) View.VISIBLE else View.GONE
}

