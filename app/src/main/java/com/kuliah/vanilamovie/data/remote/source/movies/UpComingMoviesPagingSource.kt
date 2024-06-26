package com.kuliah.vanilamovie.data.remote.source.movies

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kuliah.vanilamovie.data.model.movies.MovieDto
import com.kuliah.vanilamovie.data.remote.services.MovieApi
import com.kuliah.vanilamovie.util.Constants.ITEMS_PER_PAGE
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject


const val TAG = "PAGING SOURCE"

class UpComingMoviesPagingSource @Inject constructor (
	private val movieApi: MovieApi
)  : PagingSource<Int, MovieDto>(){

	override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {
		return state.anchorPosition
	}

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
		return try {
			val currentPage = params.key ?: 1

			val apiResponse = movieApi.fetchUpComingMovies( page = currentPage, perPage = ITEMS_PER_PAGE )
			val endOfPaginationReached = apiResponse.movies.isEmpty()

			LoadResult.Page(
				data = apiResponse.movies.shuffled(),
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