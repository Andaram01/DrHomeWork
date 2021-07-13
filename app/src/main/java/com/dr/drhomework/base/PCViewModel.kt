package com.dr.drhomework.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel


abstract class PCViewModel() : ViewModel() {



    open fun onClicking(view: View) {

    }


}
