package com.pineapplec.pokemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/* 
    Created by Carlos Piña on 21/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel
) {
    LazyVerticalGrid(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        columns = GridCells.Fixed(2),
    ) {
        items(viewModel.pokemonList.size) { index ->
            PokemonItem(pokemonName = viewModel.pokemonList[index])
        }
    }
}

@Composable
fun PokemonItem(modifier: Modifier = Modifier, pokemonName: String) {
    Card(elevation = 4.dp) {
        Text(
            modifier = modifier.padding(horizontal = 8.dp, vertical = 24.dp),
            text = pokemonName,
            textAlign = TextAlign.Center
        )
    }
}
