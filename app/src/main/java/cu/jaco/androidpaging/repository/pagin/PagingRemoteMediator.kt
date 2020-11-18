/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.montbrungroup.androidpaguin.repository.pagin

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.montbrungroup.androidpaguin.Consts
import com.montbrungroup.androidpaguin.Preferences
import com.montbrungroup.androidpaguin.database.RepoDatabase
import com.montbrungroup.androidpaguin.model.Repo
import com.montbrungroup.androidpaguin.repository.RetrofitApiService
import com.montbrungroup.androidpaguin.repository.safeapicall.ResultWrapper
import javax.inject.Inject

/**
 * This class is not in use right now
 */
@OptIn(ExperimentalPagingApi::class)
class PagingRemoteMediator @Inject constructor(
    private val service: RetrofitApiService,
    private val repoDatabase: RepoDatabase,
    private val preferences: Preferences
) : RemoteMediator<Int, Repo>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Repo>): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                state.anchorPosition ?: Consts.FIRST_PAGE
            }
            LoadType.PREPEND -> {
                if (preferences.firstPage == Consts.NO_PAGE) {
                    preferences.firstPage = Consts.FIRST_PAGE
                    Consts.FIRST_PAGE
                } else {
                    --preferences.firstPage
                }
            }
            LoadType.APPEND -> {
                if (preferences.lastPage == Consts.NO_PAGE) {
                    preferences.lastPage = Consts.FIRST_PAGE
                    Consts.FIRST_PAGE
                } else {
                    ++preferences.lastPage
                }
            }

        }

        return when (val response = service.getRepoPage(page)) {
            is ResultWrapper.Success -> {
                val dao = repoDatabase.reposDao()

                if (loadType == LoadType.REFRESH)
                    dao.clearRepos()

                dao.insertAll(response.value.items.map { it.apply { it.page = page } })

                MediatorResult.Success(endOfPaginationReached = response.value.items.isEmpty())
            }
            else -> {
                @Suppress("UNCHECKED_CAST")
                MediatorResult.Error(PagingErrorException(response as ResultWrapper<Any, Any>))
            }
        }

    }

}