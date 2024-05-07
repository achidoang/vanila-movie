package com.kuliah.vanilamovie.domain.usecases.movies

import com.kuliah.vanilamovie.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
	private val movieRepository: MovieRepository
) {
	operator fun invoke( genreId: Long ) = movieRepository.getMoviesByGenre(genreId)
}