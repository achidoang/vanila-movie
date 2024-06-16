package com.kuliah.vanilamovie.data.remote.services

import com.kuliah.vanilamovie.domain.model.ticket.Ticket
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TicketApi {
	@GET("tickets")
	suspend fun getTickets(): Response<List<Ticket>>

	@POST("tickets")
	suspend fun addTicket(@Body ticket: Ticket): Response<Ticket>
}