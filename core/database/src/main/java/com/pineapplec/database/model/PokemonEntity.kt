package com.pineapplec.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.pineapplec.database.typeConverters.StringListTypeConverter

/* 
    Created by Carlos Piña on 29/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@TypeConverters(StringListTypeConverter::class)
@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo(name = "sprite_url") val spriteUrl: String,
    val height: String,
    val weight: String,
    val hp: Int,
    val attack: Int,
    val specialAttack: Int,
    val defense: Int,
    val specialDefense: Int,
    val types: List<String>,
    @ColumnInfo(name = "specie_color_name") val specieColorName: String
)
