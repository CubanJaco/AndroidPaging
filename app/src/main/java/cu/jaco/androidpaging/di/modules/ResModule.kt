package cu.jaco.androidpaging.di.modules

import cu.jaco.androidpaging.repository.RetrofitApi
import cu.jaco.androidpaging.repository.okhttp.RetrofitClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
class ResModule {

    @Provides
    fun providerOkHttpBuilder(retrofitClient: RetrofitClient): OkHttpClient.Builder {
        return retrofitClient.okHttpBuilder
    }

    @Provides
    fun providerRetrofitBuilder(retrofitClient: RetrofitClient): Retrofit.Builder {
        return retrofitClient.retrofitBuilder
    }

    @Provides
    fun providerAuthRetrofit(retrofitClient: RetrofitClient): Retrofit {
        return retrofitClient.retrofit
    }

    @Provides
    fun providerServices(retrofitClient: RetrofitClient): RetrofitApi {
        return retrofitClient.service
    }

}