package com.pineapplec.domain

import com.pineapplec.data.PokemonRepository
import com.pineapplec.domain.model.Pokemon
import com.pineapplec.domain.model.toPokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

class GetAllPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    fun fromLocal(): Flow<List<Pokemon>> {
        return repository.getLocalPokemonList().map {
            it.map { pokemonInfo -> pokemonInfo.toPokemon() }
        }
    }

    suspend fun syncWithRemote() {
        repository.syncPokemonListWithRemote()
    }
}
