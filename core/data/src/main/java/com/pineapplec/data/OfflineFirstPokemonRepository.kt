package com.pineapplec.data

import com.pineapplec.common.mapResult
import com.pineapplec.data.model.PokemonData
import com.pineapplec.data.model.toPokemonData
import com.pineapplec.data.model.toPokemonEntity
import com.pineapplec.database.PokemonLocalDataSource
import com.pineapplec.network.PokemonRemoteDataSource
import com.pineapplec.network.model.BasicPokemonInfoApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

class OfflineFirstPokemonRepository @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val localDataSource: PokemonLocalDataSource
) : PokemonRepository {

    companion object {
        const val CORRUPT_POKEMON_ID = -1
        const val UNKNOWN_SPECIE_COLOR = ""
    }

    override fun getLocalPokemonList(): Flow<List<PokemonData>> =
        localDataSource.pokemonList.map {
            it.map { pokemonEntity -> pokemonEntity.toPokemonData() }
        }

    override suspend fun syncPokemonListWithRemote() {
        //Don't sync if database is not empty. In real production project this requirement could sync every X time
        if (!localDataSource.isEmpty()) return

        remoteDataSource.getPokemonList().onSuccess { result ->
            val pokemonData = getPokemonDataAsync(result)
            localDataSource.insertOrReplacePokemonList(pokemonData.map { it.toPokemonEntity() })
        }
    }

    private suspend fun getPokemonDataAsync(
        result: List<BasicPokemonInfoApi>
    ): List<PokemonData> = withContext(Dispatchers.IO) {
        result.map {
            val pokemonId = pokemonIdFromPokemonUrl(it.url, CORRUPT_POKEMON_ID)
            val basicPokemonData = PokemonData(pokemonId, it.name)
            fetchPokemonDataAsync(this, basicPokemonData)
        }.awaitAll().filterNot { pokemon -> pokemon.id == CORRUPT_POKEMON_ID }
    }

    private suspend fun fetchPokemonDataAsync(
        coroutineScope: CoroutineScope,
        pokemonData: PokemonData
    ): Deferred<PokemonData> = coroutineScope.async {
        val deferredPokemonData = async {
            getPokemonById(pokemonData.id).fold(
                { success ->
                    pokemonData.copy(
                        spriteUrl = success.spriteUrl,
                        height = success.height,
                        weight = success.weight,
                        types = success.types,
                        hp = success.hp,
                        attack = success.attack,
                        specialAttack = success.specialAttack,
                        defense = success.defense,
                        specialDefense = success.specialDefense,
                        specieColor = success.specieColor
                    )
                },
                { pokemonData }
            )
        }

        val deferredSpecieColor = async {
            getSpecieById(pokemonData.id).fold(
                { success -> success },
                { UNKNOWN_SPECIE_COLOR }
            )
        }
        deferredPokemonData.await().copy(specieColor = deferredSpecieColor.await())
    }

    override suspend fun getPokemonById(pokemonId: Int): Result<PokemonData> =
        remoteDataSource.getPokemonById(pokemonId).mapResult { it.toPokemonData() }

    private suspend fun getSpecieById(pokemonId: Int): Result<String> =
        remoteDataSource.getSpecieById(pokemonId).mapResult { it.color.name }
}
