package com.dr.drhomework.util.extension

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


fun EditText?.getString(): String = this?.text?.toString()?.trimEnd(' ')?.trimStart(' ') ?: ""

fun EditText.clearText() {
    this.run {
        text.clear()
        focusOn()
    }
}

/**
 * 포커스 설정
 */
fun EditText.focusOn() {
    this.run {
        isFocusableInTouchMode = true
        requestFocus()
        showKeyBoard()
    }
}


/**
 * 포커스 해제
 */
fun EditText.focusOff() {
    hideKeyboard()
    this.run {
        isFocusableInTouchMode = false
        isFocusable = false
        Handler(Looper.getMainLooper()).postDelayed({
            isFocusableInTouchMode = true
            isFocusable = true
        }, 1000)
    }
}

/**
 * 텍스트 가져온 후 초기화
 */

fun EditText.getTextAndClear(): String {
    var returnValue: String
    this.run {
        returnValue = getString()
        text.clear()
        focusOff()
    }
    return returnValue
}

/**
 * 텍스트 가져온 후 초기화
 */

fun EditText.getTextAndFocusOff(): String {
    var returnValue: String
    this.run {
        returnValue = getString()
        focusOff()
    }
    return returnValue
}


/**
 * 키보드 보임
 */
fun EditText.showKeyBoard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)

}

/**
 * 키보드 숨김
 */
fun EditText.hideKeyboard() {
    try {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (e: NullPointerException) {
        e.printStackTrace()
    }
}

/**
 * 엔터 클릭에 대해 콜백 호출
 */
fun EditText.registerEnterKeyListener(block: () -> Unit) {
    this.setOnKeyListener { _, keyCode, event ->
        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            block.invoke()
            return@setOnKeyListener true
        }

        return@setOnKeyListener false
    }
}

