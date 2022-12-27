package com.pineapplec.network.retrofit

import com.pineapplec.network.model.GetAllPokemonApiResponse
import com.pineapplec.network.model.PokemonAPI
import com.pineapplec.network.model.SpecieAPI
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

interface PokemonService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Result<GetAllPokemonApiResponse>

    @GET("pokemon/{pokemonId}")
    suspend fun getPokemonById(
        @Path("pokemonId") pokemonId: Int
    ): Result<PokemonAPI>

    @GET("pokemon-species/{pokemonId}")
    suspend fun getSpecieById(
        @Path("pokemonId") pokemonId: Int
    ): Result<SpecieAPI>
}
