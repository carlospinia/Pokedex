package com.pineapplec.data

/*
    Created by Carlos Piña on 27/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

fun pokemonIdFromPokemonUrl(pokemonUrl: String, defaultValue: Int): Int =
    pokemonUrl.split("/").filterNot { it == "" }.last().toIntOrNull() ?: defaultValue
