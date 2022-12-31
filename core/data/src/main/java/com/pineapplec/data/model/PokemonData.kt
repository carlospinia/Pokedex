package com.pineapplec.data.model

import com.pineapplec.database.model.PokemonEntity
import com.pineapplec.network.model.PokemonAPI

/* 
    Created by Carlos Piña on 27/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

data class PokemonData(
    val id: Int,
    val name: String = "",
    val spriteUrl: String = "",
    val height: String = "",
    val weight: String = "",
    val hp: Int = 0,
    val attack: Int = 0,
    val specialAttack: Int = 0,
    val defense: Int = 0,
    val specialDefense: Int = 0,
    val types: List<String> = listOf(),
    val specieColor: String = ""
)

fun PokemonAPI.toPokemonData() = PokemonData(
    id = id,
    name = name,
    spriteUrl = sprites.other.officialArtwork.spriteUrl,
    height = (height.toFloat() / 10).toString(),
    weight = (weight.toFloat() / 10).toString(),
    hp = stats.first { it.stat.name == "hp" }.baseStat,
    attack = stats.first { it.stat.name == "attack" }.baseStat,
    specialAttack = stats.first { it.stat.name == "special-attack" }.baseStat,
    defense = stats.first { it.stat.name == "defense" }.baseStat,
    specialDefense = stats.first { it.stat.name == "special-defense" }.baseStat,
    types = types.map { it.type.name }
)

fun PokemonEntity.toPokemonData() = PokemonData(
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
    specieColor = specieColorName
)

fun PokemonData.toPokemonEntity() = PokemonEntity(
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
    specieColorName = specieColor
)
