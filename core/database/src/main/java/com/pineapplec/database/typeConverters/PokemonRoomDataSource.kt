package com.pineapplec.database.typeConverters

import com.pineapplec.database.PokemonLocalDataSource
import com.pineapplec.database.dao.PokemonDao
import com.pineapplec.database.model.PokemonEntity
import kotlinx.coroutines.flow.Flow

/* 
    Created by Carlos Piña on 29/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

class PokemonRoomDataSource(
    private val pokemonDao: PokemonDao
) : PokemonLocalDataSource {

    override suspend fun isEmpty(): Boolean = (pokemonDao.movieCount() ?: 0) <= 0

    override val pokemonList: Flow<List<PokemonEntity>> =
        pokemonDao.getPokemonList()

    override suspend fun getPokemonById(pokemonId: Int): PokemonEntity? =
        pokemonDao.getPokemonByID(pokemonId)

    override suspend fun insertOrReplacePokemonList(pokemonEntityList: List<PokemonEntity>) {
        pokemonDao.insertOrReplacePokemonList(pokemonEntityList)
    }
}
