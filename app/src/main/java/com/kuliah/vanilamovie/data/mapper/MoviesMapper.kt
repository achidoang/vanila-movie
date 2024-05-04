package com.kuliah.vanilamovie.data.mapper

import com.kuliah.vanilamovie.data.model.movies.MovieDetailDTO
import com.kuliah.vanilamovie.data.model.movies.MovieDto
import com.kuliah.vanilamovie.data.model.valueObjects.CompanyDTO
import com.kuliah.vanilamovie.data.model.valueObjects.CountryDTO
import com.kuliah.vanilamovie.data.model.valueObjects.GenreDTO
import com.kuliah.vanilamovie.domain.model.movie.Movie
import com.kuliah.vanilamovie.domain.model.movie.MovieDetail
import com.kuliah.vanilamovie.domain.model.valueObjects.Company
import com.kuliah.vanilamovie.domain.model.valueObjects.Country
import com.kuliah.vanilamovie.domain.model.valueObjects.Genre

fun MovieDto.toMovie() : Movie {
	return Movie(
		id = id,
		imagePath = backdrop_path,
		title = title ?: "",
		posterPath = poster_path,
		releaseDate = release_date ?: "",
		rating = vote_average ?: 0.toDouble()
	)
}

fun MovieDetailDTO.toMovieDetail() : MovieDetail {
	return MovieDetail(
		id = id,
		budget = budget ?: 0,
		genres = genres.map { it.toGenre() },
		language = original_language ?: "",
		title = title ?: "",
		overview = overview ?: "",
		posterPath = poster_path ?: "",
		backdropPath = backdrop_path ?: "",
		productionCompanies = production_companies.map { it.toCompany() },
		productionCountries = production_countries.map { it.toCountry() },
		releaseDate = release_date,
		revenue = revenue ?: 0,
		duration = runtime ?: "",
		status = status ?: "",
		tagline = tagline ?: "",
		rating = vote_average ?: 0.toDouble()
	)
}

fun GenreDTO.toGenre() : Genre {
	return Genre(
		id = id,
		name = name ?: ""
	)
}

fun CompanyDTO.toCompany() : Company {
	return Company(
		id = id,
		logoPath = logo_path ?: "",
		name = name ?: ""
	)
}

fun CountryDTO.toCountry() : Country {
	return Country(
		name = name ?: ""
	)
}