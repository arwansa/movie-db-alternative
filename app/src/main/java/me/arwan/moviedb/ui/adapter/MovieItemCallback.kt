package me.arwan.moviedb.ui.adapter

import me.arwan.moviedb.data.model.MovieResponse

interface MovieItemCallback {
    fun onProfileClicked()
    fun onMovieItemClicked(movie: MovieResponse)
}