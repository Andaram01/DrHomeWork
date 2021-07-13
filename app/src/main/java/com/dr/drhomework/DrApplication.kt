package com.dr.drhomework

import android.app.Application
import com.dr.drhomework.item.Car
import com.dr.drhomework.item.Engine
import com.dr.drhomework.item.Wheels
import com.dr.drhomework.item.component.CarComponent
import com.dr.drhomework.item.component.DaggerCarComponent

class DrApplication: Application () {

    var carComponent: CarComponent? = null
//    lateinit var car : Car
    companion object {
        var car : Car = Car(Engine(), Wheels())
    }

    override fun onCreate() {
        super.onCreate()

        carComponent = DaggerCarComponent.builder().build()
    }
}