package com.pineapplec.pokemon.model

import android.graphics.Color
import androidx.annotation.ColorInt
import com.pineapplec.domain.model.Pokemon
import com.pineapplec.domain.model.SpecieColor

/*
    Created by Carlos Piña on 23/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

data class PokemonItem(
    val id: Int,
    val name: String,
    val spriteUrl: String,
    val types: List<String>,
    @ColorInt val specieColor: Int,
    val hp: Int,
    val attack: Int,
    val specialAttack: Int,
    val defense: Int,
    val specialDefense: Int
)

fun Pokemon.toPokemonItem() = PokemonItem(
    id = id,
    name = name,
    spriteUrl = spriteUrl,
    types = types,
    specieColor = specieColor.toColorInt(),
    hp = hp,
    attack = attack,
    specialAttack = specialAttack,
    defense = defense,
    specialDefense = specialDefense
)

private fun SpecieColor.toColorInt() = when (this) {
    SpecieColor.BLACK -> Color.BLACK
    SpecieColor.BLUE -> Color.parseColor("#77BEFE")
    SpecieColor.BROWN -> Color.parseColor("#B3726D")
    SpecieColor.GRAY -> Color.parseColor("#8E8E98")
    SpecieColor.GREEN -> Color.parseColor("#49D0B1")
    SpecieColor.PINK -> Color.parseColor("#E9CDD5")
    SpecieColor.PURPLE -> Color.parseColor("#7C528B")
    SpecieColor.RED -> Color.parseColor("#FD6C6D")
    SpecieColor.WHITE -> Color.WHITE
    SpecieColor.YELLOW -> Color.parseColor("#FFCF4A")
    SpecieColor.UNKNOWN -> Color.parseColor("#E1E4ED")
}
