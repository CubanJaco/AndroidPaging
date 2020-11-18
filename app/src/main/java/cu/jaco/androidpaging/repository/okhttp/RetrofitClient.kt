package cu.jaco.androidpaging.repository.okhttp

import android.content.Context
import com.google.gson.GsonBuilder
import cu.jaco.androidpaging.Application
import cu.jaco.androidpaging.BuildConfig
import cu.jaco.androidpaging.repository.RetrofitApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RetrofitClient @Inject constructor(val app: Application) {

    companion object {
        private const val TAG = "RetrofitClient"
        private const val BASE_URL = "https://api.github.com/"
    }

    var retrofitBuilder = providerRetrofitBuilder()
        private set
    var okHttpBuilder = providerOkHttpBuilder()
        private set
    var retrofit = providerAuthRetrofit(retrofitBuilder, okHttpBuilder)
        private set
    var service = providerServices(retrofit)
        private set

    fun invalidate() {
        retrofitBuilder = providerRetrofitBuilder()
        okHttpBuilder = providerOkHttpBuilder()
        retrofit = providerAuthRetrofit(retrofitBuilder, okHttpBuilder)
        service = providerServices(retrofit)
    }

    private fun providerOkHttpBuilder(): OkHttpClient.Builder {
        val client = OkHttpClient().newBuilder()
            .connectTimeout(15000L, TimeUnit.MILLISECONDS)
            .readTimeout(15000L, TimeUnit.MILLISECONDS)
            .writeTimeout(15000L, TimeUnit.MILLISECONDS)

        if (BuildConfig.DEBUG)
            client.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })

        return client
    }

    private fun providerRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create(SampleDeserializer.buildGson<>()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    }

    private fun providerServices(retrofit: Retrofit): RetrofitApi {
        return retrofit.create(RetrofitApi::class.java)
    }

    private fun providerAuthRetrofit(
        retrofitBuilder: Retrofit.Builder,
        okHttpClientBuilder: OkHttpClient.Builder
    ): Retrofit {

        return retrofitBuilder
            .client(okHttpClientBuilder.build())
            .baseUrl(BASE_URL)
            .build()
    }

    private fun setupCache(context: Context): Cache {
        return Cache(
            File(context.cacheDir, "responses"),
            10L * 1024L * 1024L // 10 MiB
        )
    }

}