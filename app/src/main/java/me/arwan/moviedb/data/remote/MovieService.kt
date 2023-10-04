package me.arwan.moviedb.data.remote

import me.arwan.moviedb.data.model.SearchMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET(".")
    suspend fun search(
        @Query("s") searchKey: String,
        @Query("page") page: Int,
        @Query("r") responseFormat: String = "json"
    ): Response<SearchMovieResponse>

}