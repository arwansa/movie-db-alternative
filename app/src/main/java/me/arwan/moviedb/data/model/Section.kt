package me.arwan.moviedb.data.model

data class Section(
    val title: String,
    val movieList: List<MovieResponse>,
    val isLargeThumbnail:Boolean
)
