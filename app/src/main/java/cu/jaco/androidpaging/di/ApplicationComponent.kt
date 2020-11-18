package com.montbrungroup.yumitup.di

import cu.jaco.androidpaging.Application
import cu.jaco.androidpaging.di.modules.ActivityModule
import cu.jaco.androidpaging.di.modules.ApplicationModule
import cu.jaco.androidpaging.di.modules.DatabaseModule
import cu.jaco.androidpaging.di.modules.ResModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ResModule::class,
        AndroidInjectionModule::class,
        ActivityModule::class,
        DatabaseModule::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: Application)

}