package me.arwan.moviedb.inject

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.arwan.moviedb.BuildConfig
import me.arwan.moviedb.data.remote.MovieRemoteDataSource
import me.arwan.moviedb.data.remote.MovieService
import me.arwan.moviedb.data.repository.MovieRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Key", BuildConfig.API_KEY)
            .addHeader("X-RapidAPI-Host", BuildConfig.API_HOST)
        chain.proceed(request.build())
    }.build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(movieService: MovieService) =
        MovieRemoteDataSource(movieService)

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: MovieRemoteDataSource
    ) = MovieRepository(remoteDataSource)

}