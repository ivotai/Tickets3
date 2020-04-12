package com.unicorn.tickets.app.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.unicorn.tickets.app.AppInfo
import com.unicorn.tickets.app.Configs
import com.unicorn.tickets.app.V1
import com.unicorn.tickets.app.V2
import com.unicorn.tickets.app.helper.NetworkHelper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Named(V1)
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor { chain ->
                if ("login" in chain.request().url.encodedPathSegments)
                    chain.proceed(chain.request())
                else
                    NetworkHelper.proceedRequestWithSession(chain)
            }
            .addInterceptor { chain ->
                val response = chain.proceed(chain.request())
                if (response.code != 401) return@addInterceptor response
                // 401 表示 session 过期
                NetworkHelper.proceedRequestWithNewSession(chain)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addNetworkInterceptor(StethoInterceptor())
        return  builder.build()
    }

    @Named(V1)
    @Singleton
    @Provides
    fun provideRetrofit(@Named(V1) okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Configs.baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Named(V2)
    @Singleton
    @Provides
    fun provideRetrofit2(): Retrofit {
        val builder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addNetworkInterceptor(StethoInterceptor())
        return Retrofit.Builder()
            .baseUrl(AppInfo.checkBaseUrl)
            .client(builder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}