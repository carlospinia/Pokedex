package com.pineapplec.data

import com.pineapplec.data.model.PokemonData
import kotlinx.coroutines.flow.Flow

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

interface PokemonRepository {
    fun getLocalPokemonList(): Flow<List<PokemonData>>
    suspend fun syncPokemonListWithRemote()
    suspend fun getPokemonById(pokemonId: Int): Result<PokemonData>
}
