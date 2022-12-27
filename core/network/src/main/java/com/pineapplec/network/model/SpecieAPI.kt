package com.pineapplec.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/* 
    Created by Carlos Piña on 27/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@JsonClass(generateAdapter = true)
data class SpecieAPI(
    @Json(name = "color") val color: PokemonColorAPI
)

@JsonClass(generateAdapter = true)
data class PokemonColorAPI(
    @Json(name = "name") val name: String
)
