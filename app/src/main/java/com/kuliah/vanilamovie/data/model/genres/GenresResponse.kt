package com.kuliah.vanilamovie.data.model.genres

import com.kuliah.vanilamovie.data.model.valueObjects.GenreDTO

data class GenresResponse(
	val genres: List<GenreDTO>
)