package com.montbrungroup.androidpaguin.ui.repo_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.montbrungroup.androidpaguin.Consts
import com.montbrungroup.androidpaguin.model.Repo
import com.montbrungroup.androidpaguin.repository.pagin.ApiPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoViewModel @Inject constructor(
    private val apiPagingSource: ApiPagingSource
) : ViewModel() {

    fun getRepos(): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(pageSize = Consts.PAGE_COUNT),
            pagingSourceFactory = { apiPagingSource }
        ).flow.cachedIn(viewModelScope)
    }

}