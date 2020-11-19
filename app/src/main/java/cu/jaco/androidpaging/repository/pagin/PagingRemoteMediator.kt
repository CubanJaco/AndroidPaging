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

package cu.jaco.androidpaging.repository.pagin

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import cu.jaco.androidpaging.Consts
import cu.jaco.androidpaging.database.RepoDatabase
import cu.jaco.androidpaging.model.Repo
import cu.jaco.androidpaging.repository.RetrofitApiService
import cu.jaco.androidpaging.repository.safeapicall.ResultWrapper
import java.lang.IllegalStateException
import javax.inject.Inject

/**
 * This class is not in use right now
 */
@OptIn(ExperimentalPagingApi::class)
class PagingRemoteMediator @Inject constructor(
    private val service: RetrofitApiService,
    private val repoDatabase: RepoDatabase
) : RemoteMediator<Int, Repo>() {

    companion object {
        private const val TAG = "PagingRemoteMediator"
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Repo>): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                Consts.FIRST_PAGE
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                state.pages.find { it.data.isNotEmpty() }?.data?.lastOrNull()?.page?.plus(1)
                    ?: Consts.FIRST_PAGE
            }

        }

        Log.d(TAG, "Page: $page")

        return when (val response = service.getRepoPage(page)) {
            is ResultWrapper.Success -> {
                Log.d(TAG, "Inserting in database")

                val dao = repoDatabase.reposDao()

//                if (loadType == LoadType.REFRESH) {
//                    Log.d(TAG, "Clearing database")
//                    dao.clearRepos()
//                    Log.d(TAG, "Database cleared")
//                }

                dao.insertAll(response.value.items.map { it.apply { it.page = page } })
                Log.d(TAG, "Inserted in database")

                MediatorResult.Success(endOfPaginationReached = response.value.items.isEmpty())
            }
            else -> {
                @Suppress("UNCHECKED_CAST")
                MediatorResult.Error(PagingErrorException(response as ResultWrapper<Any, Any>))
            }
        }

    }

}