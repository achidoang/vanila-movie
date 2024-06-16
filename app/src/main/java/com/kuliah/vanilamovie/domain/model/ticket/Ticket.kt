package com.kuliah.vanilamovie.domain.model.ticket

data class Ticket(
	val movieTitle: String,
	val price: Int,
	val time: String,
	val date: String,
	val seats: List<String>,
)

