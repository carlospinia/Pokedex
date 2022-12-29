package com.pineapplec.database.di

import android.content.Context
import androidx.room.Room
import com.pineapplec.database.PokedexDatabase
import com.pineapplec.database.PokemonLocalDataSource
import com.pineapplec.database.dao.PokemonDao
import com.pineapplec.database.typeConverters.PokemonRoomDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/* 
    Created by Carlos Piña on 29/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun providesLocalDataSource(
        pokemonDao: PokemonDao
    ): PokemonLocalDataSource = PokemonRoomDataSource(pokemonDao)

    @Singleton
    @Provides
    fun providesPokemonDao(
        database: PokedexDatabase
    ): PokemonDao = database.pokemonDao()

    @Provides
    @Singleton
    fun providesPokemonDatabase(
        @ApplicationContext context: Context
    ): PokedexDatabase = Room.databaseBuilder(
        context, PokedexDatabase::class.java,
        "pokedex-database"
    ).build()
}
