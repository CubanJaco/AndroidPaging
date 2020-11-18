package com.montbrungroup.androidpaguin.di.modules

import com.montbrungroup.androidpaguin.ui.repo_fragment.RepoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindFirstFragment(): RepoFragment

}