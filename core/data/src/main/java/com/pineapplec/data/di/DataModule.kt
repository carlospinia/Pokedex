package com.pineapplec.data.di

import com.pineapplec.data.OfflineFirstPokemonRepository
import com.pineapplec.data.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Singleton
    @Binds
    fun bindsPokemonRepository(
        repository: OfflineFirstPokemonRepository
    ) : PokemonRepository
}
