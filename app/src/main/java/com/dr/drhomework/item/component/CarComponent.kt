package com.dr.drhomework.item.component

import com.dr.drhomework.main.MainActivity
import com.dr.drhomework.item.module.CarModule
import com.dr.drhomework.item.module.VikeModule
import com.dr.drhomework.main.Main2Activity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CarModule::class, VikeModule::class])
interface CarComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainActivity: Main2Activity)

    @Component.Builder
    interface Builder {
        fun build() : CarComponent
        fun carModule(carModule : CarModule) : Builder
        fun vikeModule(vikeModule: VikeModule) : Builder
    }
}