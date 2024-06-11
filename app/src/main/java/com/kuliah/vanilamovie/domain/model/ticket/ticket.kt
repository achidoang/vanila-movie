package com.kuliah.vanilamovie.domain.model.ticket

data class Ticket(
	val movieTitle: String,
	val selectedSeats: String,
	val totalPrice: Int,
	val selectedTime: String,
	val selectedDate: String
)