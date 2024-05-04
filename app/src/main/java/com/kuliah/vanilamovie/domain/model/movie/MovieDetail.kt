package com.kuliah.vanilamovie.domain.model.movie

import com.kuliah.vanilamovie.domain.model.valueObjects.Company
import com.kuliah.vanilamovie.domain.model.valueObjects.Country
import com.kuliah.vanilamovie.domain.model.valueObjects.Genre

data class MovieDetail(
	val id: Int,
	val budget: Long,
	val genres: List<Genre>,
	val language: String,
	val title: String,
	val overview: String?,
	val posterPath: String?,
	val backdropPath: String?,
	val productionCompanies: List<Company>,
	val productionCountries: List<Country>,
	val releaseDate: String?,
	val revenue: Long,
	val duration: String,
	val status: String?,
	val tagline: String?,
	val rating: Double
)