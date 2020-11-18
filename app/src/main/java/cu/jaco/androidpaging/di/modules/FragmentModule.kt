package cu.jaco.androidpaging.di.modules

import cu.jaco.androidpaging.ui.repo_fragment.RepoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindFirstFragment(): RepoFragment

}