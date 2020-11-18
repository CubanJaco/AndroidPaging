package cu.jaco.androidpaging.repository

import cu.jaco.androidpaging.Consts
import cu.jaco.androidpaging.model.RepoResponse
import cu.jaco.androidpaging.repository.RetrofitApi
import cu.jaco.androidpaging.repository.safeapicall.ResultWrapper
import cu.jaco.androidpaging.repository.safeapicall.SafeApiCall
import javax.inject.Inject

class RetrofitApiService @Inject constructor(
    private val api: RetrofitApi
) : SafeApiCall() {

    suspend fun getRepoPage(page: Int): ResultWrapper<RepoResponse, String> =
        safeApiCall(String::class.java) {
            api.searchRepos(page, "Androidin:name,description", Consts.PAGE_COUNT)
        }

}