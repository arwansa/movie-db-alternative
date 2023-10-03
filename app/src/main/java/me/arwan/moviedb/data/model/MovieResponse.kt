package me.arwan.moviedb.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @SerializedName("Title")
    val title: String? = "",

    @SerializedName("Year")
    val year: Int? = 0,

    @SerializedName("imdbID")
    val imdbId: String? = "",

    @SerializedName("Type")
    val type: String? = "",

    @SerializedName("Poster")
    val poster: String? = ""

)
