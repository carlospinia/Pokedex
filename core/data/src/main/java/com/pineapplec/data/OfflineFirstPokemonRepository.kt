package com.pineapplec.data

import com.pineapplec.network.PokemonRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

class OfflineFirstPokemonRepository @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource
) : PokemonRepository {

    override fun getAllPokemon(): Flow<Result<List<String>>> = flow {
        emit(remoteDataSource.getAllPokemon())
    }
}
