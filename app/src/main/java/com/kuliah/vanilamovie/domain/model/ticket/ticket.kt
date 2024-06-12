package com.kuliah.vanilamovie.domain.model.ticket

data class Ticket(
//	val id: String,
	val movieTitle: String,
	val price: Int,
	val time: String,
	val date: String,
	val seats: List<String>,
	var isCheckedOut: Boolean = false
)

