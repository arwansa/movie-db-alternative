package me.arwan.moviedb.data.model

import com.google.gson.annotations.SerializedName

data class SearchMovieResponse(

    @SerializedName("Response")
    val response: Boolean? = false,

    @SerializedName("Search")
    val movieList: List<MovieResponse>? = emptyList(),

    @SerializedName("Error")
    val error: String? = ""

)