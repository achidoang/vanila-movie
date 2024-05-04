package com.kuliah.vanilamovie.data.model.itunes

data class ItunesResponse(
	val resultCount: Int,
	val results: List<ItuneDTO>
)