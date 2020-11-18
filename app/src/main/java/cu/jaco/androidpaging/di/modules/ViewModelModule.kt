package cu.jaco.androidpaging.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cu.jaco.androidpaging.di.annotations.ViewModelKey
import cu.jaco.androidpaging.ui.repo_fragment.RepoViewModel
import cu.jaco.androidpaging.viewmodels.ViewModelFactory
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