package com.dr.drhomework.base


import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.dr.drhomework.BuildConfig
import com.dr.drhomework.R
import com.dr.drhomework.popupManager.LoadingAnimationDialog
import com.dr.drhomework.popupManager.LoadingDialogManager
import com.dr.drhomework.util.extension.logError


import org.jetbrains.anko.toast


open class BaseActivity : AppCompatActivity() {

    protected val TAG: String = this.javaClass.name


    /**
     * closeFlag를 원상태로 다시 돌리는 핸들러이다.
     */
    private var closeFlag = false


    private val loadingAnimationDialog: LoadingAnimationDialog? by lazy {
        LoadingDialogManager.getInstance().getLoadingDialog(this)
    }


    override fun onResume() {
        super.onResume()
        if (BuildConfig.DEBUG) {
            TAG.logError("onResume activity >>>>>>>>>>>> [${javaClass.simpleName}] <<<<<<<<<<<<")
        }
    }


    //어플 종료
    fun finishApplication() {
        if (!closeFlag) {
            toast(R.string.toast_exit)
            //Flag상태값 변경
            closeFlag = true
            //2초후 Flag 원상태로 복귀.
            Handler(Looper.getMainLooper()).postDelayed({
                closeFlag = false
            }, 2000)
        } else {
            finishAffinity()
        }
    }

    fun showLoadingPopup() {
        Handler(Looper.getMainLooper()).post {
            loadingAnimationDialog?.show()
        }
    }

    fun dismissLoadingPopup(stopDismiss: (() -> Unit)? = null) {
        loadingAnimationDialog?.stopLoadingAnimation(stopDismiss) ?: stopDismiss?.invoke()
    }

}