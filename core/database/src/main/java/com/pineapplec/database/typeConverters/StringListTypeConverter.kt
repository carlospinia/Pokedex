package com.pineapplec.database.typeConverters

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/* 
    Created by Carlos Piña on 29/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

class StringListTypeConverter {
    private val moshi: Moshi = Moshi.Builder().build()
    private val type = Types.newParameterizedType(List::class.java, String::class.java)
    private val jsonAdapter: JsonAdapter<List<String>> = moshi.adapter(type)

    @TypeConverter
    fun jsonStringToStringList(string: String): List<String> =
        jsonAdapter.fromJson(string).orEmpty()

    @TypeConverter
    fun stringListToJsonString(types: List<String>): String =
        jsonAdapter.toJson(types)
}
