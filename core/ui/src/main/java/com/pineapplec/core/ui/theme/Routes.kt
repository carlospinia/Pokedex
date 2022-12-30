package com.pineapplec.core.ui.theme

/*
    Created by Carlos Piña on 25/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

sealed class Routes(val id: String) {
    companion object {
        const val POKEMON_ID = "pokemonId"
    }

    object PokemonList : Routes("PokemonList")
    object PokemonDetail : Routes("PokemonDetail/{$POKEMON_ID}") {
        fun createRoute(pokemonId: Int) = "PokemonDetail/$pokemonId"
    }
}
