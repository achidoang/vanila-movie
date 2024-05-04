package com.kuliah.vanilamovie.data.mapper

import com.kuliah.vanilamovie.data.model.itunes.ItuneDTO
import com.kuliah.vanilamovie.domain.model.itune.Itune

fun ItuneDTO.toItune() : Itune {
	return Itune(
		trackCensoredName = trackCensoredName ?: "",
		previewUrl = previewUrl,
		artworkUrl100 = artworkUrl100 ?: ""
	)
}