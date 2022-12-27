package com.pineapplec.domain.model

import com.pineapplec.data.model.PokemonData
import com.pineapplec.data.model.PokemonStatData
import com.pineapplec.data.model.PokemonTypeData

/* 
    Created by Carlos Piña on 23/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

data class Pokemon(
    val id: Int,
    val name: String,
    val spriteUrl: String,
    val height: String,
    val weight: String,
    val stats: PokemonStats,
    val types: List<String>,
    val specieColor: SpecieColor
)

data class PokemonStats(
    val hp: Int = 0,
    val attack: Int = 0,
    val specialAttack: Int = 0,
    val defense: Int = 0,
    val specialDefense: Int = 0
)

enum class SpecieColor {
    BLACK, BLUE, BROWN, GRAY, GREEN, PINK, PURPLE, RED, WHITE, YELLOW, UNKNOWN
}

fun PokemonData.toPokemon() = Pokemon(
    id = id,
    name = name,
    spriteUrl = spriteUrl,
    height = height,
    weight = weight,
    stats = stats.toPokemonStats(),
    types = types.toPokemonTypes(),
    specieColor = specieColor.toSpecieColor()
)

private fun List<PokemonStatData>.toPokemonStats(): PokemonStats {
    var pokemonStats = PokemonStats()
    forEach { stat ->
        when (stat.stat.name) {
            "hp" -> pokemonStats = pokemonStats.copy(hp = stat.baseStat)
            "attack" -> pokemonStats = pokemonStats.copy(attack = stat.baseStat)
            "special-attack" -> pokemonStats = pokemonStats.copy(specialAttack = stat.baseStat)
            "defense" -> pokemonStats = pokemonStats.copy(defense = stat.baseStat)
            "special-defense" -> pokemonStats = pokemonStats.copy(specialDefense = stat.baseStat)
        }
    }
    return pokemonStats
}

private fun List<PokemonTypeData>.toPokemonTypes(): List<String> = map { it.type.name }

private fun String.toSpecieColor() = when (this) {
    "black" -> SpecieColor.BLACK
    "blue" -> SpecieColor.BLUE
    "brown" -> SpecieColor.BROWN
    "gray" -> SpecieColor.GRAY
    "green" -> SpecieColor.GREEN
    "pink" -> SpecieColor.PINK
    "purple" -> SpecieColor.PURPLE
    "red" -> SpecieColor.RED
    "white" -> SpecieColor.WHITE
    "yellow" -> SpecieColor.YELLOW
    else -> SpecieColor.UNKNOWN
}
