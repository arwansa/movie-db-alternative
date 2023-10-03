package me.arwan.moviedb.data.remote

import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
) : BaseDataSource() {

    suspend fun search(searchKey: String, page: Int) = getResult {
        movieService.search(searchKey, page)
    }
}