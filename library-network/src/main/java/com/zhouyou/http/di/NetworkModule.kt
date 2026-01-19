package com.zhouyou.http.di

import com.zhouyou.http.interceptor.HttpLoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val DEFAULT_TIMEOUT = 10L

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor("HttpLog").apply {
//            setPrintLevel(HttpLoggingInterceptor.Level.BODY)
//            setColorLevel(java.util.logging.Level.INFO)
        }
    }
    
    @Provides
    @Singleton
    @Named("headerInterceptor")
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val builder = chain.request().newBuilder()
            // 在这里添加公共 Header，例如 Token
            // builder.addHeader("Authorization", "Bearer ...")
            chain.proceed(builder.build())
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @Named("headerInterceptor") headerInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/") 
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
