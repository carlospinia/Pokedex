package com.pineapplec.data.model

import com.pineapplec.network.model.NamedApiResourceAPI
import com.pineapplec.network.model.PokemonAPI
import com.pineapplec.network.model.PokemonMoveAPI
import com.pineapplec.network.model.PokemonStatAPI
import com.pineapplec.network.model.PokemonTypeAPI

/* 
    Created by Carlos Piña on 27/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

data class PokemonData(
    val id: Int,
    val name: String,
    val spriteUrl: String = "",
    val height: String = "",
    val weight: String = "",
    val moves: List<PokemonMoveData> = listOf(),
    val stats: List<PokemonStatData> = listOf(),
    val types: List<PokemonTypeData> = listOf(),
    val specieColor: String = ""
)

data class PokemonMoveData(
    val move: NamedApiResource
)

data class PokemonStatData(
    val stat: NamedApiResource,
    val effort: Int,
    val baseStat: Int
)

data class PokemonTypeData(
    val slot: Int,
    val type: NamedApiResource
)

data class NamedApiResource(
    val name: String,
    val url: String
)

fun PokemonAPI.toPokemonData() = PokemonData(
    id = id,
    name = name,
    spriteUrl = sprites.other.officialArtwork.spriteUrl,
    height = (height.toFloat() / 10).toString(),
    weight = (weight.toFloat() / 10).toString(),
    moves = moves.map { it.toPokemonStat() },
    stats = stats.map { it.toPokemonStat() },
    types = types.map { it.toPokemonType() }
)

private fun PokemonMoveAPI.toPokemonStat() =
    PokemonMoveData(move = move.toNamedApiResource())

private fun PokemonStatAPI.toPokemonStat() =
    PokemonStatData(stat = stat.toNamedApiResource(), effort = effort, baseStat = baseStat)

private fun PokemonTypeAPI.toPokemonType() =
    PokemonTypeData(slot = slot, type = type.toNamedApiResource())

private fun NamedApiResourceAPI.toNamedApiResource() =
    NamedApiResource(name = name, url = url)
