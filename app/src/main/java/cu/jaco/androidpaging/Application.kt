package cu.jaco.androidpaging

import android.app.Activity
import android.app.Application
import com.montbrungroup.yumitup.di.ApplicationComponent
import com.montbrungroup.yumitup.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class Application : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
        appComponent.inject(this)
    }

}