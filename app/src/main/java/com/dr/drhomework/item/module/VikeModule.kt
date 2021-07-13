package com.dr.drhomework.item.module

import com.dr.drhomework.item.Vike
import com.dr.drhomework.item.Wheels
import dagger.Module
import dagger.Provides

@Module
class VikeModule {

    @Provides
    fun provideVike() : Vike {
        return Vike(wheels = Wheels())
    }
}