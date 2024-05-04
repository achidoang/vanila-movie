package com.kuliah.vanilamovie.data.remote.source.shows

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kuliah.vanilamovie.data.model.shows.ShowDTO
import com.kuliah.vanilamovie.data.remote.services.MovieApi
import com.kuliah.vanilamovie.util.Constants.ITEMS_PER_PAGE
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class SearchShowsPagingSource @Inject constructor (
	private val movieApi: MovieApi,
	private val query: String
)  : PagingSource<Int, ShowDTO>(){

	override fun getRefreshKey(state: PagingState<Int, ShowDTO>): Int? {
		return state.anchorPosition
	}

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ShowDTO> {
		return try {
			val currentPage = params.key ?: 1

			val apiResponse = movieApi.searchShows( page = currentPage, perPage = ITEMS_PER_PAGE, query = query  )
			val endOfPaginationReached = apiResponse.tvShows.isEmpty()

			LoadResult.Page(
				data = apiResponse.tvShows ,
				prevKey = if ( currentPage == 1 ) null else currentPage - 1,
				nextKey = if ( endOfPaginationReached ) null else currentPage + 1
			)

		} catch ( ex: UnknownHostException) {
			Log.d(TAG, "$ex")
			LoadResult.Error(ex)
		} catch ( ex: HttpException) {
			Log.d(TAG, "$ex")
			LoadResult.Error(ex)
		} catch ( ex: SocketTimeoutException) {
			Log.d(TAG, "$ex")
			LoadResult.Error(ex)
		} catch ( ex: ConnectException) {
			Log.d(TAG, "$ex")
			LoadResult.Error(ex)
		}
	}

}