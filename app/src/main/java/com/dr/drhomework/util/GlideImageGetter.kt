package com.dr.drhomework.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LevelListDrawable
import android.text.Html
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget


class GlideImageGetter(private val context: Context, private val target: TextView) :
    Html.ImageGetter {
    override fun getDrawable(source: String?): Drawable {
        val drawable = LevelListDrawable()
        Glide.with(context)
            .asBitmap()
            .load(source)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    val bitmapDrawable = BitmapDrawable(resource)
                    drawable.addLevel(1, 1, bitmapDrawable)
                    drawable.setBounds(0, 0, resource.width, resource.height)
                    drawable.level = 1
                    target.invalidate()
                    target.text = target.text
                }
            })
        return drawable
    }
}