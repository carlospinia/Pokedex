package com.pineapplec.network

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

interface PokemonRemoteDataSource {
    suspend fun getAllPokemon(): Result<List<String>>
}
