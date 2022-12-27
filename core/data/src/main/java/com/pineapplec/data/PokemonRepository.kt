package com.pineapplec.data

import com.pineapplec.data.model.PokemonData

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

interface PokemonRepository {
    suspend fun getPokemonList(offset: Int, limit: Int): Result<List<PokemonData>>
    suspend fun getPokemonById(pokemonId: Int): Result<PokemonData>
}
