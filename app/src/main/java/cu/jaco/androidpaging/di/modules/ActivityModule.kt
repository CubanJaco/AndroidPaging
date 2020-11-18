package cu.jaco.androidpaging.di.modules

import cu.jaco.androidpaging.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [(FragmentModule::class)])
    internal abstract fun bindMainActivity(): MainActivity

}