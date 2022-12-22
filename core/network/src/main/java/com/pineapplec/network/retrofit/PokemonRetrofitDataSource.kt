package com.pineapplec.network.retrofit

import com.pineapplec.network.PokemonRemoteDataSource

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

class PokemonRetrofitDataSource(
    private val network: PokemonAPIService
) : PokemonRemoteDataSource {

    override suspend fun getAllPokemon(): Result<List<String>> {
        network.api.getAllPokemon().fold(
            { success -> return Result.success(success.pokemonList.map { it.name }) },
            { error -> return Result.failure(error) }
        )
    }
}
