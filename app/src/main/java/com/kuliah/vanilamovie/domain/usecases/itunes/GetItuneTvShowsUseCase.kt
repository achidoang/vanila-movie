package com.kuliah.vanilamovie.domain.usecases.itunes

import com.kuliah.vanilamovie.domain.repository.ItunesRepository
import javax.inject.Inject

class GetItuneTvShowsUseCase @Inject constructor(
	private val itunesRepository: ItunesRepository
) {
	operator fun invoke( showName: String ) = itunesRepository.getItuneTvShow(showName)
}