package com.pineapplec.pokemon.pokemonDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pineapplec.core.ui.theme.Routes.Companion.POKEMON_ID
import com.pineapplec.domain.GetPokemonByIdUseCase
import com.pineapplec.pokemon.model.PokemonItem
import com.pineapplec.pokemon.model.toPokemonItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/* 
    Created by Carlos Piña on 30/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPokemonById: GetPokemonByIdUseCase
) : ViewModel() {

    private val pokemonId = PokemonDetailArgs(savedStateHandle).pokemonId

    val uiState: StateFlow<PokemonDetailUiState> = pokemonDetailUiState().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PokemonDetailUiState.Loading
    )

    private fun pokemonDetailUiState(): Flow<PokemonDetailUiState> = flow {
        getPokemonById(pokemonId).fold(
            { success -> emit(PokemonDetailUiState.Result(success.toPokemonItem())) },
            { error -> emit(PokemonDetailUiState.Error(error)) }
        )
    }
}

internal class PokemonDetailArgs(val pokemonId: Int) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull(savedStateHandle.get<Int>(POKEMON_ID)))
}

sealed interface PokemonDetailUiState {
    object Loading : PokemonDetailUiState
    data class Result(val pokemonItem: PokemonItem) : PokemonDetailUiState
    data class Error(val error: Throwable) : PokemonDetailUiState
}
