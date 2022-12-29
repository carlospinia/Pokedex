package com.pineapplec.database

import com.pineapplec.database.model.PokemonEntity
import kotlinx.coroutines.flow.Flow

/* 
    Created by Carlos Piña on 29/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

interface PokemonLocalDataSource {
    suspend fun isEmpty(): Boolean
    val pokemonList: Flow<List<PokemonEntity>>
    suspend fun getPokemonById(pokemonId: Int): PokemonEntity
    suspend fun insertOrReplacePokemonList(pokemonEntityList: List<PokemonEntity>)
}
