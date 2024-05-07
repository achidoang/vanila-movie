package com.kuliah.vanilamovie.domain.usecases.itunes

import com.kuliah.vanilamovie.domain.repository.ItunesRepository
import javax.inject.Inject

class GetItuneMoviesUseCase @Inject constructor(
	private val itunesRepository: ItunesRepository
) {
	operator fun invoke( movieName: String ) = itunesRepository.getItuneMovies(movieName)
}