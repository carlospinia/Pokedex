package com.pineapplec.network

import com.pineapplec.network.model.BasicPokemonInfoApi

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

interface PokemonRemoteDataSource {
    suspend fun getAllPokemon(): Result<List<BasicPokemonInfoApi>>
}
