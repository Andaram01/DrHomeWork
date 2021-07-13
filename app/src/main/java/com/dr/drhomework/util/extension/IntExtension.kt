package com.dr.drhomework.util.extension

import android.content.res.Resources


/**
 * Dp값을 PX로 변환
 */
fun Int.dpToPixel(): Int =
    (this * Resources.getSystem().displayMetrics.density).toInt()

