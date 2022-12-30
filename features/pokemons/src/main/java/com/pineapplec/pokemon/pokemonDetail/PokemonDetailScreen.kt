package com.pineapplec.pokemon.pokemonDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pineapplec.common.POKEMON_SPRITE_URL
import com.pineapplec.core.ui.R
import com.pineapplec.core.ui.theme.Loader
import com.pineapplec.pokemon.model.PokemonItem
import kotlinx.coroutines.flow.collectLatest

/* 
    Created by Carlos Piña on 25/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val pokemonDetailUiState by produceState<PokemonDetailUiState>(
        initialValue = PokemonDetailUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collectLatest { value = it }
        }
    }

    when (pokemonDetailUiState) {
        PokemonDetailUiState.Loading -> Loader()
        is PokemonDetailUiState.Error -> TODO()
        is PokemonDetailUiState.Result -> {
            val pokemonItem = (pokemonDetailUiState as PokemonDetailUiState.Result).pokemonItem
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(android.graphics.Color.parseColor("#77BEFE")))
            ) {
                PokemonDetailNavBar()
                PokemonDetail(pokemonItem)
            }
        }
    }
}

@Composable
fun PokemonDetailNavBar() {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "back button",
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun PokemonDetail(pokemonItem: PokemonItem) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(POKEMON_SPRITE_URL.format(pokemonItem.id.toFloat()))
            .crossfade(true)
            .placeholder(R.drawable.pokeball_placeholder)
            .error(R.drawable.pokeball_placeholder)
            .build(),
        contentDescription = "pokemon image",
        modifier = Modifier.fillMaxWidth(),
        alignment = Alignment.Center
    )
}
