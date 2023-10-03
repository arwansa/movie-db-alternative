package me.arwan.moviedb.data.repository

import me.arwan.moviedb.data.remote.MovieRemoteDataSource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
) {

    suspend fun search(searchKey: String, page: Int) =
        remoteDataSource.search(searchKey, page)

}