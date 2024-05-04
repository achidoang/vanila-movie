package com.kuliah.vanilamovie.domain.repository

import androidx.paging.PagingData
import com.kuliah.vanilamovie.domain.model.shows.Show
import com.kuliah.vanilamovie.domain.model.shows.ShowDetail
import com.kuliah.vanilamovie.domain.model.valueObjects.Genre
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {
	fun fetchTopRatedTvShows() : Flow<PagingData<Show>>
	fun fetchPopularTvShows() : Flow<PagingData<Show>>
	fun fetchOnAirTvShows() : Flow<PagingData<Show>>
	fun searchShows( query: String ) : Flow<PagingData<Show>>
	fun fetchShowDetails( showId: Int ) : Flow<ShowDetail>
	fun getShowsGenres() : Flow<List<Genre>>
	fun fetchShowsByGenre( genreId: Long ) : Flow<PagingData<Show>>
}