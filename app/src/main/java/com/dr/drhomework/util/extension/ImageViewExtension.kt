package com.dr.drhomework.util.extension

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions

fun ImageView.setImageUrlNonResizeNonChche(url: String?, placeholder: Int = 0) {
    if (activityIsActive().not()) return

    if (url.isNullOrEmpty() && placeholder != 0) {
        Glide.with(this).load(placeholder).into(this)
    } else {
        url.getGlideCheckGif(this).load(url).diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true).placeholder(placeholder).into(this)
    }
}

fun ImageView.setImageUrl(url: String?, placeholder: Int = 0) {
    if (activityIsActive().not()) return

    if (url.isNullOrEmpty() && placeholder != 0) {
        Glide.with(this).load(placeholder).into(this)
    } else {
        url.getGlideCheckGif(this).load(url).placeholder(placeholder).into(this)
    }
}


fun ImageView.setImageUrlCenterCrop(url: String?, placeholder: Int = 0) {
    if (activityIsActive().not()) return

    if (url.isNullOrEmpty() && placeholder != 0) {
        Glide.with(this).load(placeholder)
            .apply(RequestOptions().transform(CenterCrop())).into(this)
    } else {
        url.getGlideCheckGif(this).load(url)
            .apply(RequestOptions().transform(CenterCrop())).placeholder(placeholder).into(this)
    }
}


/**
 * loadImageDrawable
 * ------------------------------------------------------------------------------------------------
 */


fun ImageView.activityIsActive(): Boolean {
    (context as? AppCompatActivity)?.also { activity ->
        if (activity.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            return true
        } else if (activity.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED).not()) {
            return false
        }
    }
    return true
}

fun String?.getGlideCheckGif(view: ImageView): RequestManager {
    return Glide.with(view).also {
        if (this.checkGif()) {
            it.asGif()
        }
    }
}

fun String?.checkGif(): Boolean = (!this.isNullOrEmpty() && contains("gif"))

