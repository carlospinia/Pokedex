package com.pineapplec.pokemon.pokemonList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pineapplec.core.ui.R
import com.pineapplec.core.ui.theme.Routes
import com.pineapplec.pokemon.model.PokemonItem
import kotlinx.coroutines.flow.collectLatest
import java.util.*

/* 
    Created by Carlos Piña on 21/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val pokemonUiState by produceState<PokemonListUiState>(
        initialValue = PokemonListUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collectLatest { value = it }
        }
    }

    (pokemonUiState as? PokemonListUiState.Result)?.let { result ->
        LazyVerticalGrid(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            columns = GridCells.Fixed(2),
        ) {
            items(result.pokemonList.size) { index ->
                PokemonItem(pokemon = result.pokemonList[index], navController = navController)
            }
        }
    }
}

@Composable
fun PokemonItem(
    modifier: Modifier = Modifier,
    pokemon: PokemonItem,
    navController: NavController
) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.clickable {
            navController.navigate(Routes.PokemonDetail.createRoute(pokemon.id))
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.spriteUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.pokeball_placeholder)
                    .error(R.drawable.pokeball_placeholder)
                    .build(),
                contentDescription = "${pokemon.name} image",
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
            )
            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                text = pokemon.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                    else it.toString()
                },
                textAlign = TextAlign.Center
            )
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokemonItem(pokemonId = 1, pokemonName = "Pikachu")
}*/
