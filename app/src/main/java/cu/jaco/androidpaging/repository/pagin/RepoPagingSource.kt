package cu.jaco.androidpaging.repository.pagin

import androidx.paging.PagingSource
import cu.jaco.androidpaging.Consts
import cu.jaco.androidpaging.model.Repo
import cu.jaco.androidpaging.repository.RetrofitApiService
import cu.jaco.androidpaging.repository.safeapicall.ResultWrapper
import javax.inject.Inject

/**
 * This class is not in use right now
 */
class RepoPagingSource @Inject constructor(
    private val api: RetrofitApiService
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {

        try {
            // Start refresh at page 1 if undefined.
            val nextPage = params.key ?: Consts.FIRST_PAGE

            return when (val response = api.getRepoPage(nextPage)) {
                is ResultWrapper.Success -> {
                    LoadResult.Page(
                        data = response.value.items,
                        prevKey = if (nextPage == Consts.FIRST_PAGE) null else nextPage - 1,
                        nextKey = nextPage + 1
                    )
                }
                else -> {
                    @Suppress("UNCHECKED_CAST")
                    LoadResult.Error(PagingErrorException(response as ResultWrapper<Any, Any>))
                }
            }

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }

}