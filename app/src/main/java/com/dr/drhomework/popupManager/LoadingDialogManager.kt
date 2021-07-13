package com.dr.drhomework.popupManager


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import com.dr.drhomework.util.extension.hideKeyBoard


class LoadingDialogManager {

    companion object {
        @Volatile
        private var instance: LoadingDialogManager? = null

        @JvmStatic
        fun getInstance(): LoadingDialogManager =
            instance ?: synchronized(this) {
                instance ?: LoadingDialogManager().also {
                    instance = it
                }
            }
    }


    private var isShowDialog = false

    fun getLoadingDialog(context: Context?): LoadingAnimationDialog? {
        if (isShowDialog || context == null) {
            return null
        }
        (context as? Activity)?.run {
            if (isDestroyed) {
                return null
            } else {
                hideKeyBoard()
            }
        }

        isShowDialog = true
        Handler(Looper.getMainLooper()).postDelayed({
            isShowDialog = false
        }, 500)


        LoadingAnimationDialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen) {
            isShowDialog = false
        }.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
        }.let {
            return it
        }
    }
}