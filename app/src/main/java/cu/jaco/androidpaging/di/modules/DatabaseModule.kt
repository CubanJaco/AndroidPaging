package cu.jaco.androidpaging.di.modules

import cu.jaco.androidpaging.Application
import cu.jaco.androidpaging.database.RepoDatabase
import cu.jaco.androidpaging.repository.okhttp.RetrofitClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class DatabaseModule {

    @Provides
    fun providerOkHttpBuilder(app: Application): RepoDatabase {
        return RepoDatabase.getInstance(app)
    }

}