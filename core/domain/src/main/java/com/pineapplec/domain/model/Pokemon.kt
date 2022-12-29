package com.pineapplec.domain.model

import com.pineapplec.data.model.PokemonData

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
    val hp: Int,
    val attack: Int,
    val specialAttack: Int,
    val defense: Int,
    val specialDefense: Int,
    val types: List<String>,
    val specieColor: SpecieColor
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
    hp = hp,
    attack = attack,
    specialAttack = specialAttack,
    defense = defense,
    specialDefense = specialDefense,
    types = types,
    specieColor = specieColor.toSpecieColor()
)

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
