package me.arwan.moviedb.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(

    @SerializedName("Response")
    val response: Boolean? = false,

    @SerializedName("Search")
    val search: List<MovieResponse>? = emptyList(),

    @SerializedName("Error")
    val error: String? = ""

)