package cu.jaco.androidpaging.repository

import cu.jaco.androidpaging.model.RepoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("search/repositories?sort=stars")
    suspend fun searchRepos(
        @Query("page") page: Int,
        @Query("q") query: String,
        @Query("per_page") itemsPerPage: Int
    ): RepoResponse

}