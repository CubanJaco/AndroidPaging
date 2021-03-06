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

package cu.jaco.androidpaging.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cu.jaco.androidpaging.model.Repo

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Repo>)

    @Query("SELECT * FROM repo ORDER BY stargazers_count DESC, name ASC")
    fun reposPaging(): PagingSource<Int, Repo>

    @Query("SELECT * FROM repo WHERE page is :page ORDER BY stargazers_count DESC, name ASC")
    suspend fun reposByPage(page: Int): List<Repo>

    @Query("SELECT * FROM repo ORDER BY stargazers_count ASC, name DESC LIMIT 1")
    suspend fun firstRepo(): Repo?

    @Query("SELECT * FROM repo ORDER BY stargazers_count DESC, name ASC LIMIT 1")
    suspend fun lastRepo(): Repo?

    @Query("DELETE FROM repo")
    suspend fun clearRepos()

}