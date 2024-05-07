package com.kuliah.vanilamovie.domain.usecases.shows

import com.kuliah.vanilamovie.domain.repository.ShowsRepository
import javax.inject.Inject

class GetOnAirTvShowsUseCase @Inject constructor(
	private val showsRepository: ShowsRepository
) {
	operator fun invoke() = showsRepository.fetchOnAirTvShows()
}