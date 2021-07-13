package com.dr.drhomework.intro

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager

import com.dr.drhomework.base.BaseActivity
import com.dr.drhomework.main.MainActivity


class SplashActivity : BaseActivity(){

    companion object {
        private const val INTRO_DELAY = 1000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowLayoutParams()
        pendingStartMain()
    }

    private fun setWindowLayoutParams() {
        window.setFlags(
            //스테이터스바와 네비게이션바 투명 처리
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    /**
     * 추후 Splash 화면에서 필요한 비즈니스 로직 실행 후 호출로 변경
     */
    private fun pendingStartMain() {
        Handler(Looper.getMainLooper()).postDelayed({
            MainActivity.start(this)
        }, INTRO_DELAY)
    }

}