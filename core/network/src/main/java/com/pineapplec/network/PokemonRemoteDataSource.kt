package com.pineapplec.network

import com.pineapplec.network.model.BasicPokemonInfoApi
import com.pineapplec.network.model.PokemonAPI
import com.pineapplec.network.model.SpecieAPI

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

interface PokemonRemoteDataSource {
    suspend fun getPokemonList(offset: Int, limit: Int): Result<List<BasicPokemonInfoApi>>
    suspend fun getPokemonById(pokemonId: Int): Result<PokemonAPI>
    suspend fun getSpecieById(pokemonId: Int): Result<SpecieAPI>
}
