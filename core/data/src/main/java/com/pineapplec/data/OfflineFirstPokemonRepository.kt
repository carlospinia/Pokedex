package com.pineapplec.data

import com.pineapplec.common.mapResult
import com.pineapplec.data.model.PokemonData
import com.pineapplec.network.PokemonRemoteDataSource
import javax.inject.Inject

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

class OfflineFirstPokemonRepository @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource
) : PokemonRepository {

    override suspend fun getAllPokemon(): Result<List<PokemonData>> =
        remoteDataSource.getAllPokemon().mapResult {
            it.map { basicPokemonInfoApi ->
                PokemonData(name = basicPokemonInfoApi.name, url = basicPokemonInfoApi.url)
            }
        }
}
