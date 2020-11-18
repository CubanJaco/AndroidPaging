package cu.jaco.androidpaging.repository.pagin

import android.util.Log
import androidx.paging.PagingSource
import cu.jaco.androidpaging.Consts
import cu.jaco.androidpaging.database.RepoDatabase
import cu.jaco.androidpaging.model.Repo
import cu.jaco.androidpaging.repository.RetrofitApiService
import cu.jaco.androidpaging.repository.safeapicall.ResultWrapper
import javax.inject.Inject

class ApiPagingSource @Inject constructor(
    private val api: RetrofitApiService,
    private val repoDatabase: RepoDatabase
) : PagingSource<Int, Repo>() {

    companion object {
        private const val TAG = "ApiPagingSourFce"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {

        val dao = repoDatabase.reposDao()

        //obtener el numero de pagina actual
        val nextPage = params.key ?: Consts.FIRST_PAGE

        Log.d(TAG, "Load page $nextPage.")

        //intentar obtener la pagina desde la red
        val api = loadFromApi(nextPage)

        if (api is LoadResult.Page && api.data.isNotEmpty()) {

            Log.d(TAG, "Api request successfull. Saving in database.")

            //guardar el request satisfactorio en la base de datos
            if (params.key ?: Consts.FIRST_PAGE == Consts.FIRST_PAGE) {
                Log.d(TAG, "Clearing database.")
                dao.clearRepos()
                Log.d(TAG, "Database cleared.")
            }

            dao.insertAll(api.data.map { it.apply { it.page = nextPage } })
            Log.d(TAG, "Data saved in database.")

        }

        //request de la api satisfactorio
        if (api is LoadResult.Page) {
            Log.d(TAG, "Api request successfull with no data.")
            return api //retornar la respuesta
        }

        Log.d(TAG, "Api error. Loading from database.")

        //intentar obtener la pagina desde la base de datos
        val repos = dao.reposByPage(nextPage)

        //si hay datos en la base de datos, retornar esos datos
        return if (repos.isNotEmpty()) {
            Log.d(TAG, "Data loaded from database.")
            LoadResult.Page(
                data = repos,
                prevKey = if (nextPage == Consts.FIRST_PAGE) null else nextPage - 1,
                nextKey = nextPage + 1
            )
        } else {
            Log.d(TAG, "No data from database. Returning error.")
            api
        }

    }

    private suspend fun loadFromApi(nextPage: Int): LoadResult<Int, Repo> {

        try {
            // Start refresh at page 1 if undefined.
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