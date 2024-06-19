package com.kuliah.vanilamovie.presentation.viewModel.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuliah.vanilamovie.data.remote.services.TicketApi
import com.kuliah.vanilamovie.di.TicketApiModule
import com.kuliah.vanilamovie.domain.model.ticket.Ticket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class TicketViewModel : ViewModel() {
	private val _ticketState = MutableStateFlow<List<Ticket>>(emptyList())
	val ticketState: StateFlow<List<Ticket>> = _ticketState

	private val _errorState = MutableStateFlow<String?>(null)
	val errorState: StateFlow<String?> = _errorState

	private val ticketApi: TicketApi by lazy {
		TicketApiModule.instance.create(TicketApi::class.java)
	}

	// Function to add ticket locally and to API
	fun addTicket(ticket: Ticket) {
		// Save ticket locally
		_ticketState.value = _ticketState.value + ticket

		// Save ticket to API
		viewModelScope.launch {
			try {
				val response = ticketApi.addTicket(ticket)
				if (response.isSuccessful) {
					// Update ticketState after adding ticket to API
					loadTicketsFromApi()
					_errorState.value = null
				} else {
					// Handle API error
					// For example, log the error or show a message to the user
					_errorState.value = "Unable to save ticket to the server."
				}
			} catch (e: Exception) {
				// Handle exception (network error, etc.)
				_errorState.value = "Unable to save ticket to the server."
			}
		}
	}

	// Function to load tickets from API
	fun loadTicketsFromApi() {
		viewModelScope.launch {
			try {
				val response = ticketApi.getTickets()
				if (response.isSuccessful) {
					val tickets = response.body() ?: emptyList()
					_ticketState.value = tickets
					_errorState.value = null
				} else {
					// Handle API error
					// For example, log the error or show a message to the user
					_errorState.value = "Unable to connect to the server."
				}
			} catch (e: Exception) {
				// Handle exception (network error, etc.)
				_errorState.value = "Unable to connect to the server."
			}
		}
	}
}