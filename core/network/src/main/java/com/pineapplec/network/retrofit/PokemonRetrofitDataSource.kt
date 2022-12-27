package com.pineapplec.network.retrofit

import com.pineapplec.network.PokemonRemoteDataSource
import com.pineapplec.network.model.BasicPokemonInfoApi
import com.pineapplec.network.model.PokemonAPI
import com.pineapplec.network.model.SpecieAPI

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

class PokemonRetrofitDataSource(
    private val network: PokemonAPIService
) : PokemonRemoteDataSource {

    override suspend fun getPokemonList(
        offset: Int,
        limit: Int
    ): Result<List<BasicPokemonInfoApi>> {
        network.api.getPokemonList(offset, limit).fold(
            { success -> return Result.success(success.pokemonList) },
            { error -> return Result.failure(error) }
        )
    }

    override suspend fun getPokemonById(pokemonId: Int): Result<PokemonAPI> {
        network.api.getPokemonById(pokemonId).fold(
            { success -> return Result.success(success) },
            { error -> return Result.failure(error) }
        )
    }

    override suspend fun getSpecieById(pokemonId: Int): Result<SpecieAPI> {
        network.api.getSpecieById(pokemonId).fold(
            { success -> return Result.success(success) },
            { error -> return Result.failure(error) }
        )
    }
}
