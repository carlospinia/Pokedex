package com.pineapplec.domain

import com.pineapplec.common.mapResult
import com.pineapplec.data.PokemonRepository
import com.pineapplec.domain.model.Pokemon
import com.pineapplec.domain.model.toPokemon
import javax.inject.Inject

/* 
    Created by Carlos Piña on 30/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

class GetPokemonByIdUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(pokemonId: Int): Result<Pokemon> =
        repository.getPokemonById(pokemonId).mapResult { it.toPokemon() }
}
