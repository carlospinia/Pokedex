package com.pineapplec.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@JsonClass(generateAdapter = true)
data class GetAllPokemonApiResponse(
    @Json(name = "next") val next: String,
    @Json(name = "results") val pokemonList: List<BasicPokemonInfoApi>
)
