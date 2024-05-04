package com.kuliah.vanilamovie.data.repository

import android.util.Log
import com.kuliah.vanilamovie.data.mapper.toItune
import com.kuliah.vanilamovie.data.remote.services.ItunesApi
import com.kuliah.vanilamovie.domain.model.itune.Itune
import com.kuliah.vanilamovie.domain.repository.ItunesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ItunesRepositoryImpl @Inject constructor(
	private val itunesApi: ItunesApi
) : ItunesRepository {

	companion object {
		const val TAG = "ItunesRepositoryImp"
	}
	override fun getItuneMovies(movieName: String ): Flow<List<Itune>> {
		return flow {
			emit( itunesApi.searchItuneMovie( movieName ).results.map { it.toItune() } )
		}.flowOn(Dispatchers.IO).catch {
			Log.i(TAG, "$it")
		}
	}

	override fun getItuneTvShow(showName: String): Flow<List<Itune>> {
		return flow {
			emit( itunesApi.searchItuneTvShow ( showName ).results.map { it.toItune() } )
		}.flowOn(Dispatchers.IO).catch {
			Log.i(TAG, "$it")
		}
	}

}