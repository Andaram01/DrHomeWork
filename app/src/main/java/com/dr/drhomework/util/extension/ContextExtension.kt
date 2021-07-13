package com.dr.drhomework.util.extension

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources


fun Context.getDrawableCompat(@DrawableRes drawableId: Int): Drawable? {
    return try {
        AppCompatResources.getDrawable(this, drawableId)
    } catch (e: Resources.NotFoundException) {
        null
    } catch (e: ExceptionInInitializerError) {
        null
    }
}

