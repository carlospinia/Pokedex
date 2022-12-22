package com.pineapplec.network.di

import com.pineapplec.network.PokemonRemoteDataSource
import com.pineapplec.network.retrofit.PokemonAPIService
import com.pineapplec.network.retrofit.PokemonRetrofitDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun bindRemoteDataSource(): PokemonRemoteDataSource =
        PokemonRetrofitDataSource(PokemonAPIService)
}
