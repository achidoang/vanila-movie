package com.kuliah.vanilamovie.util

import androidx.datastore.preferences.core.booleanPreferencesKey

object Constants {
	const val API_KEY = "e9e19696e6505d46c7f78669622ddd2d"
	const val MOVIE_BASE_URL = "https://api.themoviedb.org/3/"
	const val ITUNES_BASE_URL = "https://itunes.apple.com/"
	const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original"
	const val BASE_IMAGE_URL_W500 = "https://image.tmdb.org/t/p/w500"
	const val ITEMS_PER_PAGE = 10
	val THEME_MODE_KEY = booleanPreferencesKey("THEME_MODE_KEY")
}