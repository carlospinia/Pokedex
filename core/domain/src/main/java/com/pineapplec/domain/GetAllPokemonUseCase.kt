package com.pineapplec.domain

import androidx.annotation.CheckResult
import com.pineapplec.data.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

class GetAllPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    @CheckResult
    operator fun invoke(): Flow<Result<List<String>>> {
        return repository.getAllPokemon()
    }
}
