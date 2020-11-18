package com.montbrungroup.androidpaguin.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.montbrungroup.androidpaguin.di.annotations.ViewModelKey
import com.montbrungroup.androidpaguin.ui.repo_fragment.RepoViewModel
import com.montbrungroup.androidpaguin.viewmodels.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RepoViewModel::class)
    internal abstract fun bindRepoViewModel(viewModel: RepoViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}