package com.pineapplec.pokemon.pokemonDetail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pineapplec.common.POKEMON_SPRITE_URL
import com.pineapplec.core.ui.R

/* 
    Created by Carlos Piña on 25/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@Composable
fun PokemonDetailScreen(
    pokemonId: Int
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(POKEMON_SPRITE_URL.format(pokemonId.toFloat()))
            .crossfade(true)
            .placeholder(R.drawable.pokeball_placeholder)
            .error(R.drawable.pokeball_placeholder)
            .build(),
        contentDescription = "pokemon image",
        modifier = Modifier.fillMaxWidth(),
        alignment = Alignment.Center
    )
}
