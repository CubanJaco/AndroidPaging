package com.montbrungroup.androidpaguin.di.modules

import com.montbrungroup.androidpaguin.Application
import com.montbrungroup.androidpaguin.database.RepoDatabase
import com.montbrungroup.androidpaguin.repository.okhttp.RetrofitClient
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