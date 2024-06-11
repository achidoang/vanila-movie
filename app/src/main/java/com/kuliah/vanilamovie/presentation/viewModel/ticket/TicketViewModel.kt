package com.kuliah.vanilamovie.presentation.viewModel.ticket

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.kuliah.vanilamovie.domain.model.ticket.Ticket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TicketViewModel : ViewModel() {
	private val _tickets = MutableStateFlow<List<Ticket>>(emptyList())
	val tickets: StateFlow<List<Ticket>> = _tickets

	fun addTicket(ticket: Ticket) {
		_tickets.value = _tickets.value + ticket
	}
}