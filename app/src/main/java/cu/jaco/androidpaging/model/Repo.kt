/*
 * Copyright (C) 2018 The Android Open Source Project
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

package cu.jaco.androidpaging.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import cu.jaco.androidpaging.Consts

/**
 * Immutable model class for a Github repo that holds all the information about a repository.
 * Objects of this type are received from the Github API, therefore all the fields are annotated
 * with the serialized name.
 * This class also defines the Room repos table, where the repo [id] is the primary key.
 */
@Entity(tableName = "repo")
data class Repo(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    val id: Long,
    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    val name: String,
    @ColumnInfo(name = "full_name")
    @field:SerializedName("full_name")
    val fullName: String,
    @ColumnInfo(name = "description")
    @field:SerializedName("description")
    val description: String?,
    @ColumnInfo(name = "html_url")
    @field:SerializedName("html_url")
    val url: String,
    @ColumnInfo(name = "stargazers_count")
    @field:SerializedName("stargazers_count")
    val stars: Int,
    @ColumnInfo(name = "forks_count")
    @field:SerializedName("forks_count")
    val forks: Int,
    @ColumnInfo(name = "language")
    @field:SerializedName("language")
    val language: String?,
    @ColumnInfo(name = "page")
    @get:SerializedName("page")
    var page: Int = Consts.NO_PAGE
)
