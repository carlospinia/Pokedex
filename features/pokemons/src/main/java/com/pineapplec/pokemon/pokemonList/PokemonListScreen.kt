package com.pineapplec.pokemon.pokemonList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pineapplec.core.ui.R
import com.pineapplec.core.ui.theme.Loader
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

    var listVisibility by remember { mutableStateOf(false) }
    when (pokemonUiState) {
        PokemonListUiState.Loading -> {
            Loader()
            listVisibility = false
        }
        is PokemonListUiState.Result -> {
            val pokemonList = (pokemonUiState as PokemonListUiState.Result).pokemonList
            AnimatedVisibility(
                visible = listVisibility,
                enter = fadeIn(animationSpec = tween(1_000)),
                exit = fadeOut(animationSpec = tween(1_000))
            ) {
                PokemonList(pokemonList, navController)
            }
            listVisibility = true
        }
    }
}

@Composable
private fun PokemonList(
    pokemonList: List<PokemonItem>,
    navController: NavController
) {
    LazyVerticalGrid(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        columns = GridCells.Fixed(2),
    ) {
        items(pokemonList.size, key = { pokemonList[it].id }) { index ->
            PokemonItem(
                pokemon = pokemonList[index],
                navController = navController
            )
        }
    }
}

@Composable
fun PokemonItem(
    pokemon: PokemonItem,
    navController: NavController,
) {
    Card(
        elevation = 4.dp,
        backgroundColor = Color(pokemon.specieColor),
        modifier = Modifier.clickable {
            navController.navigate(Routes.PokemonDetail.createRoute(pokemon.id))
        }
    ) {
        Column(Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp)
                    .fillMaxWidth(),
                text = pokemon.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                    else it.toString()
                },
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                color = if (pokemon.specieColor == Color.White.toArgb()) Color.Gray else Color.White,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PokemonTypesChips(pokemon = pokemon)
                PokemonImage(pokemon = pokemon)
            }
        }
    }
}

@Composable
fun PokemonTypesChips(pokemon: PokemonItem) {
    Column(Modifier.padding(top = 8.dp)) {
        pokemon.types.forEachIndexed { index, type ->
            Card(
                backgroundColor = if (pokemon.specieColor == Color.Black.toArgb())
                    Color.DarkGray
                else Color(
                    pokemon.specieColor
                ).copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                    text = type.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                        else it.toString()
                    },
                    fontSize = 12.sp,
                    color = if (pokemon.specieColor == Color.White.toArgb()) Color.Gray else Color.White
                )
            }
            if (index < pokemon.types.lastIndex) {
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun PokemonImage(pokemon: PokemonItem) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.pokeball_placeholder),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.2f)
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pokemon.spriteUrl)
                .crossfade(true)
                .build(),
            contentDescription = "${pokemon.name} image",
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        )
    }
}
