package com.pineapplec.network.retrofit

import com.pineapplec.network.model.GetAllPokemonApiResponse
import com.pineapplec.network.model.PokemonAPI
import com.pineapplec.network.model.SpecieAPI
import retrofit2.http.GET
import retrofit2.http.Path

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

interface PokemonService {

    @GET("pokemon?offset=0&limit=30")
    suspend fun getPokemonList(): Result<GetAllPokemonApiResponse>

    @GET("pokemon/{pokemonId}")
    suspend fun getPokemonById(
        @Path("pokemonId") pokemonId: Int
    ): Result<PokemonAPI>

    @GET("pokemon-species/{pokemonId}")
    suspend fun getSpecieById(
        @Path("pokemonId") pokemonId: Int
    ): Result<SpecieAPI>
}
