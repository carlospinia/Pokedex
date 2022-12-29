package com.pineapplec.pokemon.pokemonList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pineapplec.domain.GetAllPokemonUseCase
import com.pineapplec.domain.model.Pokemon
import com.pineapplec.pokemon.model.PokemonItem
import com.pineapplec.pokemon.model.toPokemonItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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
        getAllPokemon.fromLocal().map(
            ::handleGetAllPokemonFromLocal
        ).also {
            syncPokemonList()
        }

    private fun handleGetAllPokemonFromLocal(pokemonList: List<Pokemon>): PokemonListUiState =
        when (pokemonList.isEmpty()) {
            true -> PokemonListUiState.Loading
            false -> PokemonListUiState.Result(pokemonList.map { it.toPokemonItem() })
        }

    private fun syncPokemonList() = viewModelScope.launch {
        getAllPokemon.syncWithRemote()
    }
}

sealed interface PokemonListUiState {
    object Loading : PokemonListUiState
    data class Result(val pokemonList: List<PokemonItem>) : PokemonListUiState
}
