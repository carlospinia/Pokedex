package com.pineapplec.network.retrofit

import com.pineapplec.network.PokemonRemoteDataSource
import com.pineapplec.network.model.BasicPokemonInfoApi

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

class PokemonRetrofitDataSource(
    private val network: PokemonAPIService
) : PokemonRemoteDataSource {

    override suspend fun getAllPokemon(): Result<List<BasicPokemonInfoApi>> {
        network.api.getAllPokemon().fold(
            { success -> return Result.success(success.pokemonList) },
            { error -> return Result.failure(error) }
        )
    }
}
