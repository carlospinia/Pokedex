package com.pineapplec.domain

import androidx.annotation.CheckResult
import com.pineapplec.common.mapResult
import com.pineapplec.data.PokemonRepository
import com.pineapplec.domain.model.POKEMON_SPRITE_URL
import com.pineapplec.domain.model.Pokemon
import javax.inject.Inject

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

class GetAllPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    companion object {
        const val CORRUPT_POKEMON_ID = -1
    }

    @CheckResult
    suspend operator fun invoke(): Result<List<Pokemon>> {
        return repository.getAllPokemon().mapResult {
            it.map { pokemonInfo ->
                val pokemonId = pokemonIdFromPokemonUrl(pokemonInfo.url)
                Pokemon(
                    id = pokemonId,
                    name = pokemonInfo.name,
                    spriteUrl = getPokemonUrl(pokemonId)
                )
            }.filterNot { pokemon -> pokemon.id == CORRUPT_POKEMON_ID }
        }
    }

    private fun pokemonIdFromPokemonUrl(pokemonUrl: String): Int =
        pokemonUrl.split("/").filterNot { it == "" }.last().toIntOrNull() ?: CORRUPT_POKEMON_ID

    private fun getPokemonUrl(pokemonId: Int): String =
        POKEMON_SPRITE_URL.format(pokemonId.toFloat())
}
