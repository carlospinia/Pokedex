package com.pineapplec.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/* 
    Created by Carlos Piña on 27/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@JsonClass(generateAdapter = true)
data class PokemonAPI(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "sprites") val sprites: PokemonSpritesAPI,
    @Json(name = "height") val height: Int,
    @Json(name = "weight") val weight: Int,
    @Json(name = "moves") val moves: List<PokemonMoveAPI>,
    @Json(name = "stats") val stats: List<PokemonStatAPI>,
    @Json(name = "types") val types: List<PokemonTypeAPI>
)

@JsonClass(generateAdapter = true)
data class PokemonSpritesAPI(
    @Json(name = "other") val other: SpriteOtherAPI
)

@JsonClass(generateAdapter = true)
data class SpriteOtherAPI(
    @Json(name = "official-artwork") val officialArtwork: SpriteOfficialArtwork
)

@JsonClass(generateAdapter = true)
data class SpriteOfficialArtwork(
    @Json(name = "front_default") val spriteUrl: String
)

@JsonClass(generateAdapter = true)
data class PokemonMoveAPI(
    @Json(name = "move") val move: NamedApiResourceAPI
)

@JsonClass(generateAdapter = true)
data class PokemonStatAPI(
    @Json(name = "stat") val stat: NamedApiResourceAPI,
    @Json(name = "effort") val effort: Int,
    @Json(name = "base_stat") val baseStat: Int
)

@JsonClass(generateAdapter = true)
data class PokemonTypeAPI(
    @Json(name = "slot") val slot: Int,
    @Json(name = "type") val type: NamedApiResourceAPI
)

@JsonClass(generateAdapter = true)
data class NamedApiResourceAPI(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)

