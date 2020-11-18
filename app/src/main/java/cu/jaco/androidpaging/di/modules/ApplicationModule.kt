package com.montbrungroup.androidpaguin.di.modules

import android.content.Context
import com.montbrungroup.androidpaguin.Application
import dagger.Module
import dagger.Provides

@Module(includes = [(ViewModelModule::class)])
class ApplicationModule {
    @Provides
    fun providersApplication(app: Application): Context = app
}