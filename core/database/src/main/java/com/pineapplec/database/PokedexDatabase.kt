package com.pineapplec.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pineapplec.database.dao.PokemonDao
import com.pineapplec.database.model.PokemonEntity

/* 
    Created by Carlos Piña on 29/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@Database(
    version = 1,
    entities = [PokemonEntity::class]
)
abstract class PokedexDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
