package com.pineapplec.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pineapplec.domain.GetAllPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/* 
    Created by Carlos Piña on 21/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getAllPokemon: GetAllPokemonUseCase
) : ViewModel() {

    val uiState: StateFlow<PokemonListUiState> = pokemonListUiState().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PokemonListUiState.Loading
    )

    private fun pokemonListUiState(): Flow<PokemonListUiState> =
        getAllPokemon().map { result ->
            PokemonListUiState.Result(result.getOrDefault(listOf()))
        }
}

sealed interface PokemonListUiState {
    object Loading : PokemonListUiState
    data class Result(val pokemonList: List<String>) : PokemonListUiState
}
