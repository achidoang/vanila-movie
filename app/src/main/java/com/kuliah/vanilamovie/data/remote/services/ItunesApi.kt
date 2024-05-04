package com.kuliah.vanilamovie.data.remote.services

import com.kuliah.vanilamovie.data.model.itunes.ItunesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {
	@GET("search?media=movie")
	suspend fun searchItuneMovie(
		@Query("term") name: String
	) : ItunesResponse

	@GET("search?media=tvShow")
	suspend fun searchItuneTvShow(
		@Query("term") name: String
	) : ItunesResponse
}