package com.kuliah.vanilamovie.domain.repository

import androidx.paging.PagingData
import com.kuliah.vanilamovie.domain.model.movie.Movie
import com.kuliah.vanilamovie.domain.model.movie.MovieDetail
import com.kuliah.vanilamovie.domain.model.valueObjects.Genre
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
	fun getUpComingMovies() : Flow<PagingData<Movie>>
	fun getNowPlayingMovies() : Flow<PagingData<Movie>>
	fun getPopularMovies() : Flow<PagingData<Movie>>
	fun getTopRatedMovies() : Flow<PagingData<Movie>>
	fun getMoviesGenre() : Flow<List<Genre>>
	fun getMoviesByGenre( genreId: Long ) : Flow<PagingData<Movie>>
	fun getTrendingMovies() : Flow<PagingData<Movie>>
	fun searchMovies( query: String ) : Flow<PagingData<Movie>>
	fun getMovieDetail( movieId: Int ) : Flow<MovieDetail>
}