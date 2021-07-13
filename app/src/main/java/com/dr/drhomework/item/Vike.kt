package com.dr.drhomework.item

import javax.inject.Inject

class Vike constructor(wheels: Wheels){

    val drivingState = "오토바이 운전중"

    fun driving() : String{
        println(drivingState)

        return drivingState
    }
}