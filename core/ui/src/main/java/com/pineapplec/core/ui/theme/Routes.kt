package com.pineapplec.core.ui.theme

/*
    Created by Carlos Piña on 25/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

sealed class Routes(val id: String) {
    companion object {
        const val POKEMON_DETAIL_SPRITE_URL = "spriteUrl"
    }

    object PokemonList : Routes("PokemonList")
    object PokemonDetail : Routes("PokemonDetail/{$POKEMON_DETAIL_SPRITE_URL}") {
        fun createRoute(pokemonId: Int) = "PokemonDetail/$pokemonId"
    }
}
