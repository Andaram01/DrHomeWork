
package com.dr.drhomework.util.extension

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.dr.drhomework.R


fun Activity.transitionRtoL() {
    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
}

fun Activity.transitionLtoR() {
    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
}


/**
 * 키보드 숨김
 */
fun Activity.hideKeyBoard() {
    try {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    } catch (e: NullPointerException) {
        e.printStackTrace()
    }
}