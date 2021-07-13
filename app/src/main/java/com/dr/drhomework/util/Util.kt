package com.dr.drhomework.util

import android.content.Context
import android.util.TypedValue

object Util {
    // dp를 px로 변환
    fun dpToPx(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp,
            context.resources.displayMetrics
        ).toInt()
    }
}