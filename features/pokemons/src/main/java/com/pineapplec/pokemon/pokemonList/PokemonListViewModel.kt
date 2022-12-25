package com.pineapplec.pokemon.pokemonList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pineapplec.domain.GetAllPokemonUseCase
import com.pineapplec.domain.model.Pokemon
import com.pineapplec.pokemon.model.PokemonItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
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

    private fun pokemonListUiState(): Flow<PokemonListUiState> = flow {
        getAllPokemon().fold(
            { success -> emit(uiStateFromSuccess(success)) },
            { emit(PokemonListUiState.Result(listOf())) }
        )
    }

    private fun uiStateFromSuccess(pokemonList: List<Pokemon>): PokemonListUiState =
        PokemonListUiState.Result(
            pokemonList.map {
                PokemonItem(id = it.id, name = it.name, spriteUrl = it.spriteUrl)
            }
        )
}

sealed interface PokemonListUiState {
    object Loading : PokemonListUiState
    data class Result(val pokemonList: List<PokemonItem>) : PokemonListUiState
}
