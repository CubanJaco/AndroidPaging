package com.montbrungroup.yumitup.di

import com.montbrungroup.androidpaguin.Application
import com.montbrungroup.androidpaguin.di.modules.ActivityModule
import com.montbrungroup.androidpaguin.di.modules.ApplicationModule
import com.montbrungroup.androidpaguin.di.modules.DatabaseModule
import com.montbrungroup.androidpaguin.di.modules.ResModule
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