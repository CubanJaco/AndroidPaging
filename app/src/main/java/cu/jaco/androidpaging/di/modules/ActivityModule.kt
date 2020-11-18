package com.montbrungroup.androidpaguin.di.modules

import com.montbrungroup.androidpaguin.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [(FragmentModule::class)])
    internal abstract fun bindMainActivity(): MainActivity

}