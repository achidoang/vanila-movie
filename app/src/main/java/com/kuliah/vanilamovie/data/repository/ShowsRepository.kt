package com.kuliah.vanilamovie.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.kuliah.vanilamovie.data.mapper.toGenre
import com.kuliah.vanilamovie.data.mapper.toShow
import com.kuliah.vanilamovie.data.mapper.toShowDetail
import com.kuliah.vanilamovie.data.remote.services.MovieApi
import com.kuliah.vanilamovie.data.remote.source.shows.OnAirTvShowsPagingSource
import com.kuliah.vanilamovie.data.remote.source.shows.PopularTvShowsPagingSource
import com.kuliah.vanilamovie.data.remote.source.shows.SearchShowsPagingSource
import com.kuliah.vanilamovie.data.remote.source.shows.ShowsByGenrePagingSource
import com.kuliah.vanilamovie.data.remote.source.shows.TopRatedTvShowsPagingSource
import com.kuliah.vanilamovie.domain.model.shows.Show
import com.kuliah.vanilamovie.domain.model.shows.ShowDetail
import com.kuliah.vanilamovie.domain.model.valueObjects.Genre
import com.kuliah.vanilamovie.domain.repository.ShowsRepository
import com.kuliah.vanilamovie.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShowsRepositoryImpl @Inject constructor(
	private val movieApi: MovieApi,
) : ShowsRepository {

	companion object {
		const val TAG = "ShowsRepositoryImpl"
	}

	override fun fetchTopRatedTvShows(): Flow<PagingData<Show>> {
		return Pager(
			config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
			pagingSourceFactory = { TopRatedTvShowsPagingSource( movieApi ) }
		).flow.map {
			it.map {
				it.toShow()
			}
		}.flowOn(Dispatchers.IO)
	}

	override fun fetchPopularTvShows(): Flow<PagingData<Show>> {
		return Pager(
			config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
			pagingSourceFactory = { PopularTvShowsPagingSource( movieApi ) }
		).flow.map {
			it.map {
				it.toShow()
			}
		}.flowOn(Dispatchers.IO)
	}

	override fun fetchShowDetails(showId: Int): Flow<ShowDetail> {
		return flow {
			emit( movieApi.fetchShowDetail(showId).toShowDetail() )
		}.flowOn(Dispatchers.IO).catch {
			Log.i(TAG, "$it")
		}
	}

	override fun fetchShowsByGenre(genreId: Long): Flow<PagingData<Show>> {
		return Pager(
			config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
			pagingSourceFactory = { ShowsByGenrePagingSource( movieApi, genreId ) }
		).flow.map {
			it.map {
				it.toShow()
			}
		}.flowOn(Dispatchers.IO)
	}

	override fun getShowsGenres(): Flow<List<Genre>> {
		return flow {
			emit( movieApi.fetchShowsGenre().genres.map { it.toGenre() } )
		}.flowOn( Dispatchers.IO ).catch {
			Log.i(TAG, "$it")
		}
	}

	override fun searchShows(query: String): Flow<PagingData<Show>> {
		return Pager(
			config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
			pagingSourceFactory = { SearchShowsPagingSource( movieApi, query) }
		).flow.map {
			it.map {
				it.toShow()
			}
		}.flowOn(Dispatchers.IO)
	}

	override fun fetchOnAirTvShows(): Flow<PagingData<Show>> {
		return Pager(
			config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
			pagingSourceFactory = { OnAirTvShowsPagingSource( movieApi ) }
		).flow.map {
			it.map {
				it.toShow()
			}
		}.flowOn(Dispatchers.IO)
	}

}