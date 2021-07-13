
package com.dr.drhomework.popupManager

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.dr.drhomework.R


class LoadingAnimationDialog(
    private val contextValue: Context,
    style: Int = android.R.style.Theme_Translucent_NoTitleBar_Fullscreen,
    private val callBackDismiss: (() -> Unit)
) : Dialog(contextValue, style) {

    companion object {
        private const val LOADING_TIMEOUT: Long = 10000L
        private const val END_TIMEOUT: Long = 500L
    }

    private var checkEndTime = false

    private val stopHandler = Handler(Looper.getMainLooper())
    private val stopRunnable = Runnable {
        checkEndTime = false
        stopLoadingAnimation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowManager.LayoutParams().let {
            window?.attributes = it
        }
        setContentView(R.layout.dialog_loading_view)
    }

    override fun show() {
        super.show()
        checkEndTime = true

        stopHandler.postDelayed(stopRunnable, LOADING_TIMEOUT)
    }

    override fun dismiss() {
        super.dismiss()
        stopHandler.removeCallbacks(stopRunnable)
    }

    fun stopLoadingAnimation(stopDismiss: (() -> Unit)? = null) =
        stopLoadingAnimation(true, stopDismiss)

    private fun stopLoadingAnimation(hasDelay: Boolean, stopDismiss: (() -> Unit)? = null) {
        if (!isShowing) {
            stopDismiss?.invoke()
            return
        }
        if (checkEndTime && hasDelay) {
            stopHandler.removeCallbacks(stopRunnable)
            Handler(Looper.getMainLooper()).postDelayed({
                (contextValue as? Activity)?.let {
                    if (!it.isFinishing) {
                        callBackDismiss.invoke()
                        stopDismiss?.invoke()
                        dismiss()
                    }

                } ?: let {
                    callBackDismiss.invoke()
                    stopDismiss?.invoke()
                    dismiss()
                }
            }, END_TIMEOUT)
        } else {
            (contextValue as? Activity)?.let {
                if (!it.isFinishing) {
                    callBackDismiss.invoke()
                    stopDismiss?.invoke()
                    dismiss()
                }

            } ?: let {
                callBackDismiss.invoke()
                stopDismiss?.invoke()
                dismiss()
            }
        }
    }
}
