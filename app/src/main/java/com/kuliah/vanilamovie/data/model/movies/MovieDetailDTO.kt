package com.kuliah.vanilamovie.data.model.movies

import com.kuliah.vanilamovie.data.model.valueObjects.CompanyDTO
import com.kuliah.vanilamovie.data.model.valueObjects.CountryDTO
import com.kuliah.vanilamovie.data.model.valueObjects.GenreDTO

data class MovieDetailDTO(
	val id: Int,
	val budget: Long?,
	val genres: List<GenreDTO>,
	val original_language: String?,
	val title: String?,
	val overview: String?,
	val poster_path: String?,
	val backdrop_path: String?,
	val production_companies: List<CompanyDTO>,
	val production_countries: List<CountryDTO>,
	val release_date: String?,
	val revenue: Long?,
	val runtime: String?,
	val status: String?,
	val tagline: String?,
	val vote_average: Double?
)