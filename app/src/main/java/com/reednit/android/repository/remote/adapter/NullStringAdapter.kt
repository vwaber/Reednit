package com.reednit.android.repository.remote.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader

private const val DEFAULT_STRING: String = ""

class NullStringAdapter {

    @SuppressWarnings("unused")
    @FromJson
    fun fromJson(reader: JsonReader): String {

        if(reader.peek() == JsonReader.Token.NULL){
            reader.nextNull<Unit>()
            return DEFAULT_STRING
        }

        return reader.nextString()

    }

}