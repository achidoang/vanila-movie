package com.kuliah.vanilamovie.domain.model.movie

data class Movie(
	val id: Int,
	val imagePath: String?,
	val title: String,
	val posterPath: String?,
	val releaseDate: String,
	val rating: Double
)
