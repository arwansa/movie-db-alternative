package me.arwan.moviedb

import me.arwan.moviedb.data.model.MovieResponse

interface MovieItemCallback {
    fun onProfileClicked()
    fun onMovieItemClicked(movie: MovieResponse)
}