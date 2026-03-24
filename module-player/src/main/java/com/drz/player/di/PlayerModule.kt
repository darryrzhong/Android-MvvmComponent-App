package com.drz.player.di

import com.drz.player.data.api.PlayerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlayerModule {

    @Provides
    @Singleton
    fun providePlayerApi(retrofit: Retrofit): PlayerApi =
        retrofit.create(PlayerApi::class.java)
}
