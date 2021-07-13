package com.dr.drhomework.util.extension

import android.util.Log
import com.dr.drhomework.BuildConfig


fun String.logDebug(logData: String) {
    if (BuildConfig.DEBUG) {
        Log.d(this, logData)
    }
}

fun String.logError(logData: String) {
    if (BuildConfig.DEBUG) {
        Log.e(this, logData)
    }
}

fun String?.isNotNullEmpty(): Boolean {
    return isNullOrEmpty().not()
}
