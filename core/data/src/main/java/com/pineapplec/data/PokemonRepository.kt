package com.pineapplec.data

import kotlinx.coroutines.flow.Flow

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

interface PokemonRepository {
    fun getAllPokemon(): Flow<Result<List<String>>>
}
