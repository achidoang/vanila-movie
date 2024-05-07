package com.kuliah.vanilamovie.domain.usecases.movies

import com.kuliah.vanilamovie.domain.repository.MovieRepository
import javax.inject.Inject

class GetUpComingMoviesUseCase @Inject constructor(
	private val movieRepository: MovieRepository
) {
	operator fun invoke() = movieRepository.getUpComingMovies()
}