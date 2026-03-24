package com.drz.community.di

import com.drz.community.data.api.CommunityApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommunityModule {
    @Provides @Singleton
    fun provideCommunityApi(retrofit: Retrofit): CommunityApi = retrofit.create(CommunityApi::class.java)
}
