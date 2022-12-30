package com.pineapplec.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pineapplec.database.model.PokemonEntity
import kotlinx.coroutines.flow.Flow

/* 
    Created by Carlos Piña on 29/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@Dao
interface PokemonDao {

    @Query("SELECT COUNT(id) FROM pokemon")
    suspend fun movieCount(): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplacePokemonList(pokemonList: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon")
    fun getPokemonList(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getPokemonByID(id: Int): PokemonEntity?
}
