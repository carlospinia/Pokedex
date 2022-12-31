package com.pineapplec.pokemon.pokemonDetail

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
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
import java.util.*
import com.pineapplec.feature_pokemon.R as PokemonDetailR

/* 
    Created by Carlos Piña on 25/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

val IMAGE_CONTAINER_SIZE = 300.dp

@Composable
fun PokemonDetailScreen(
    onBackPressed: () -> Unit,
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
        is PokemonDetailUiState.Error -> Snackbar {
            Text(text = LocalContext.current.getString(PokemonDetailR.string.pokemon_detail_error))
        }
        is PokemonDetailUiState.Result -> {
            val pokemonItem = (pokemonDetailUiState as PokemonDetailUiState.Result).pokemonItem
            Column(
                modifier = Modifier.background(Color(pokemonItem.specieColor))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PokemonDetailNavBar(pokemonItem.specieColor, onBackPressed)
                    PokemonDetail(pokemonItem)
                }
                PokemonInfoCard(pokemonItem)
            }
        }
    }
}

@Composable
fun PokemonDetailNavBar(specieColor: Int, onBackPressed: () -> Unit) {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "back button",
        tint = if (specieColor == Color.White.toArgb()) Color.Gray else Color.White,
        modifier = Modifier.clickable { onBackPressed() }
    )
}

@Composable
fun PokemonDetail(pokemonItem: PokemonItem) {
    PokemonNameAndId(pokemonItem)
    PokemonTypesChips(pokemonItem)
}

@Composable
fun PokemonNameAndId(pokemonItem: PokemonItem) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = pokemonItem.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                else it.toString()
            },
            color = if (pokemonItem.specieColor == Color.White.toArgb()) Color.Gray else Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f)
        )
        Text(
            text = "#${pokemonItem.id}",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = if (pokemonItem.specieColor == Color.White.toArgb()) Color.Gray else Color.White
        )
    }
}

@Composable
fun PokemonTypesChips(pokemon: PokemonItem) {
    Row {
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
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                    text = type.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                        else it.toString()
                    },
                    fontSize = 16.sp,
                    color = if (pokemon.specieColor == Color.White.toArgb()) Color.Gray else Color.White
                )
            }
            if (index < pokemon.types.lastIndex) {
                Spacer(Modifier.width(4.dp))
            }
        }
    }
}

@Composable
fun PokemonInfoCard(pokemonItem: PokemonItem) {
    ConstraintLayout {
        val (placeHolder, card, image) = createRefs()
        PokemonPlaceHolder(
            modifier = Modifier.constrainAs(placeHolder) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Card(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(card) {
                    top.linkTo(placeHolder.bottom, margin = -(60.dp))
                },
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            backgroundColor = Color.White,
            elevation = 16.dp
        ) {
            PokemonStats(pokemonItem)
        }
        PokemonImage(pokemonItem, modifier = Modifier.constrainAs(image) {
            top.linkTo(placeHolder.top)
            bottom.linkTo(placeHolder.bottom)
            start.linkTo(placeHolder.start)
            end.linkTo(placeHolder.end)
        })
    }
}

@Composable
fun PokemonPlaceHolder(modifier: Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.pokeball_placeholder),
        contentDescription = null,
        tint = Color.White,
        modifier = modifier
            .width(IMAGE_CONTAINER_SIZE)
            .height(IMAGE_CONTAINER_SIZE)
            .alpha(0.2f)
    )
}

@Composable
fun PokemonImage(pokemonItem: PokemonItem, modifier: Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(POKEMON_SPRITE_URL.format(pokemonItem.id.toFloat()))
            .crossfade(true)
            .build(),
        contentDescription = "${pokemonItem.name} image",
        modifier = modifier
            .width(IMAGE_CONTAINER_SIZE - 40.dp)
            .height(IMAGE_CONTAINER_SIZE - 40.dp)
    )
}

@Composable
fun PokemonStats(pokemonItem: PokemonItem) {
    Column(
        modifier = Modifier.padding(top = 40.dp)
    ) {
        PokemonStat("HP", pokemonItem.hp.toFloat(), pokemonItem.specieColor)
        PokemonStat("Attack", pokemonItem.attack.toFloat(), pokemonItem.specieColor)
        PokemonStat("Sp. Atk", pokemonItem.specialAttack.toFloat(), pokemonItem.specieColor)
        PokemonStat("Defense", pokemonItem.defense.toFloat(), pokemonItem.specieColor)
        PokemonStat("Sp. Def", pokemonItem.specialDefense.toFloat(), pokemonItem.specieColor)
    }
}

@Composable
fun PokemonStat(statName: String, statValue: Float, specieColor: Int) {
    var uiStatValue by remember { mutableStateOf(0f) }
    Row(
        modifier = Modifier.padding(top = 8.dp, start = 24.dp, end = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = statName,
            color = Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(0.2f)
        )
        Text(
            text = statValue.toInt().toString(),
            color = Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(0.1f)
        )
        val progressAnimDuration = 1_000
        val progressAnimation by animateFloatAsState(
            targetValue = (uiStatValue / 100) / 2,
            animationSpec = tween(
                durationMillis = progressAnimDuration,
                easing = FastOutSlowInEasing
            )
        )
        LinearProgressIndicator(
            progress = progressAnimation,
            modifier = Modifier.weight(0.7f),
            color = if (specieColor == Color.White.toArgb())
                Color.Gray
            else
                Color(specieColor)
        )
    }
    LaunchedEffect(statValue) {
        uiStatValue = statValue
    }

}
