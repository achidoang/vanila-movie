package com.kuliah.vanilamovie.domain.repository

import com.kuliah.vanilamovie.domain.model.itune.Itune
import kotlinx.coroutines.flow.Flow

interface ItunesRepository  {
	fun getItuneMovies( movieName: String ) : Flow<List<Itune>>
	fun getItuneTvShow( showName: String ) : Flow<List<Itune>>
}