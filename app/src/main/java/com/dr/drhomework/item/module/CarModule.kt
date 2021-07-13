package com.dr.drhomework.item.module

import com.dr.drhomework.item.Car
import com.dr.drhomework.item.Engine
import com.dr.drhomework.item.Wheels
import dagger.Module
import dagger.Provides

@Module
class CarModule {

    @Provides
    fun provideCar() : Car {
        return Car(engine = Engine(), wheels = Wheels())
    }
}