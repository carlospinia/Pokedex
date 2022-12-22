package com.pineapplec.network.retrofit

import com.pineapplec.network.errorHandling.ResultCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/* 
    Created by Carlos Piña on 22/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

object PokemonAPIService {

    private val okHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    val api: PokemonService = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(ResultCallAdapterFactory())
        .build()
        .run {
            create(PokemonService::class.java)
        }
}
