package com.kuliah.vanilamovie.data.mapper

import com.kuliah.vanilamovie.data.model.shows.ShowDTO
import com.kuliah.vanilamovie.data.model.shows.ShowDetailDTO
import com.kuliah.vanilamovie.data.model.valueObjects.LanguageDTO
import com.kuliah.vanilamovie.data.model.valueObjects.NetworkDTO
import com.kuliah.vanilamovie.data.model.valueObjects.ShowCreatorDTO
import com.kuliah.vanilamovie.domain.model.shows.Show
import com.kuliah.vanilamovie.domain.model.shows.ShowDetail
import com.kuliah.vanilamovie.domain.model.valueObjects.Language
import com.kuliah.vanilamovie.domain.model.valueObjects.Network
import com.kuliah.vanilamovie.domain.model.valueObjects.ShowCreator

fun ShowDTO.toShow() : Show {
	return Show(
		id = id,
		name = name ?: "",
		posterPath = poster_path ?: "",
		rating = vote_average ?: 0.toDouble(),
		firstAirDate = first_air_date ?: ""
	)
}

fun ShowDetailDTO.toShowDetail() : ShowDetail {
	return ShowDetail(
		id = id,
		createdBy = created_by.map { it.toShowCreator() },
		firstAirDate = first_air_date ?: "",
		lastAirDate = last_air_date ?: "",
		genres = genres.map { it.toGenre() },
		spokenLanguages = spoken_languages.map { it.toLanguage() },
		name = name ?: "",
		networks = networks.map { it.toNetwork() },
		numberOfEpisodes = number_of_episodes ?: 0,
		numberOfSeasons = number_of_seasons ?: 0,
		productionCompanies = production_companies.map { it.toCompany() },
		productionCountries = production_countries.map { it.toCountry() },
		posterPath = poster_path,
		backdropPath = backdrop_path ?: "",
		overview = overview ?: "",
		status = status,
		tagline = tagline,
		rating = vote_average ?: 0.toDouble()
	)
}

fun ShowCreatorDTO.toShowCreator() : ShowCreator {
	return ShowCreator(
		id = id,
		name = name ?: "",
		profilePath = profile_path
	)
}

fun LanguageDTO.toLanguage() : Language {
	return Language(
		name = english_name ?: ""
	)
}

fun NetworkDTO.toNetwork() : Network {
	return Network(
		id = id,
		logoPath = logo_path,
		name = name ?: ""
	)
}
