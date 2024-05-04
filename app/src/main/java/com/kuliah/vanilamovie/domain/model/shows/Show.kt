package com.kuliah.vanilamovie.domain.model.shows

data class Show(
	val id: Int,
	val name:String,
	val posterPath: String,
	val rating: Double,
	val firstAirDate: String
)
