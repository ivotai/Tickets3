package com.unicorn.tickets.app.di.module

import com.blankj.utilcode.util.ToastUtils
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.unicorn.tickets.app.*
import com.unicorn.tickets.app.helper.NetworkHelper
import com.unicorn.tickets.data.event.Logout
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
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
            .addInterceptor { NetworkHelper.closeConnection(it) }
            .addInterceptor { chain ->
                if ("login" in chain.request().url.encodedPathSegments || "checkDevice" in chain.request().url.encodedPathSegments)
                    chain.proceed(chain.request())
                else
                    NetworkHelper.proceedRequestWithSession(chain)
            }
            .addInterceptor(
                object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val response = chain.proceed(chain.request())
                        if (response.code == 401) {
                            RxBus.post(Logout())
                        }
                        return  response
                            // 1. 出现 401
                            // 2. 持续 401
                            // 3. 好像不使用 return chain.proceed(chain.request().newBuilder().build()) 也行，在请求 401 后又进行了一次请求
                            // 4. chain.call().cancel() 成功取消第二次请求，日志如下：
                            /*
                            2022-06-22 15:51:14.119 26405-26840/com.unicorn.tickets D/OkHttp: --> GET http://cs.lefz.kjgk.xyz/api/v1/pda/batteryCar/order/list?beginDate=2022%2F06%2F22&endDate=2022%2F06%2F22&page=1&pageSize=10&conductorId=667394168628183040&terminalEquipmentTag=665db67b8a2eb5fe&travelDate=2022%2F06%2F22
                            2022-06-22 15:51:14.120 26405-26840/com.unicorn.tickets D/OkHttp: Connection: close
                            2022-06-22 15:51:14.120 26405-26840/com.unicorn.tickets D/OkHttp: Cookie: SESSION=NTk5YTZmOTItYjJiNS00OGNjLWIyZmMtNTNhZjllZjhmN2Jm
                            2022-06-22 15:51:14.120 26405-26840/com.unicorn.tickets D/OkHttp: --> END GET
                            2022-06-22 15:51:14.120 26405-26840/com.unicorn.tickets D/OkHttp: <-- HTTP FAILED: java.io.IOException: Canceled
                             */
                            // 5. 但回调收到了 java.io.IOException: Canceled

                            // todo 测试
                            // 第一步 测试必须要 newBuilder

                            // 第三步 测试 cancel 能否取消 return 的这个请求
                            // 第四部 测试 Logout()
                    }
                }
            )
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
//            .addNetworkInterceptor(StethoInterceptor())
        return builder.build()
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
            .addInterceptor { NetworkHelper.closeConnection(it) }
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