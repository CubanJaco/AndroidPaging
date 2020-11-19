package cu.jaco.androidpaging.ui.repo_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cu.jaco.androidpaging.Consts
import cu.jaco.androidpaging.database.RepoDatabase
import cu.jaco.androidpaging.model.Repo
import cu.jaco.androidpaging.repository.RetrofitApiService
import cu.jaco.androidpaging.repository.pagin.PagingRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoViewModel @Inject constructor(
    private val apiService: RetrofitApiService,
    private val database: RepoDatabase
) : ViewModel() {

    fun getRepos(): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = Consts.PAGE_COUNT,
                initialLoadSize = Consts.PAGE_COUNT
            ),
            remoteMediator = PagingRemoteMediator(apiService, database),
            pagingSourceFactory = {
                database.reposDao().reposPaging()
            }
        ).flow.cachedIn(viewModelScope)
    }

}