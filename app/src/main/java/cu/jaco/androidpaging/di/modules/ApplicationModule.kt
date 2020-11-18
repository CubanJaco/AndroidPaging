package cu.jaco.androidpaging.di.modules

import android.content.Context
import cu.jaco.androidpaging.Application
import dagger.Module
import dagger.Provides

@Module(includes = [(ViewModelModule::class)])
class ApplicationModule {
    @Provides
    fun providersApplication(app: Application): Context = app
}