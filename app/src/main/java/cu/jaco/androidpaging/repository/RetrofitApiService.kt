package com.montbrungroup.androidpaguin.repository

import com.montbrungroup.androidpaguin.Consts
import com.montbrungroup.androidpaguin.model.RepoResponse
import com.montbrungroup.androidpaguin.repository.safeapicall.ResultWrapper
import com.montbrungroup.androidpaguin.repository.safeapicall.SafeApiCall
import javax.inject.Inject

class RetrofitApiService @Inject constructor(
    private val api: RetrofitApi
) : SafeApiCall() {

    suspend fun getRepoPage(page: Int): ResultWrapper<RepoResponse, String> =
        safeApiCall(String::class.java) {
            api.searchRepos(page, "Androidin:name,description", Consts.PAGE_COUNT)
        }

}