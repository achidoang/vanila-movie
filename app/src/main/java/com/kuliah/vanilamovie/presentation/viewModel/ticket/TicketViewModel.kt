package com.kuliah.vanilamovie.presentation.viewModel.ticket

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuliah.vanilamovie.domain.model.ticket.Ticket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TicketViewModel : ViewModel() {
	private val _ticketState = MutableStateFlow<List<Ticket>>(emptyList())
	val ticketState: StateFlow<List<Ticket>> = _ticketState

	fun addTicket(ticket: Ticket) {
		if (!_ticketState.value.contains(ticket)) {
			_ticketState.value = _ticketState.value + ticket
		}
	}
}
