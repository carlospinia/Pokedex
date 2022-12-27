package com.pineapplec.domain

import androidx.annotation.CheckResult
import com.pineapplec.common.mapResult
import com.pineapplec.data.PokemonRepository
import com.pineapplec.domain.model.Pokemon
import com.pineapplec.domain.model.toPokemon
import javax.inject.Inject

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

class GetAllPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    @CheckResult
    suspend operator fun invoke(offset: Int = 0, limit: Int = 30): Result<List<Pokemon>> {
        return repository.getPokemonList(offset, limit).mapResult {
            it.map { pokemonInfo -> pokemonInfo.toPokemon() }
        }
    }
}
