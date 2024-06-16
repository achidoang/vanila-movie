package com.kuliah.vanilamovie.presentation.viewModel.ticket

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuliah.vanilamovie.data.remote.services.TicketApi
import com.kuliah.vanilamovie.di.RetrofitClient
import com.kuliah.vanilamovie.domain.model.ticket.Ticket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

//class TicketViewModel : ViewModel() {
//	private val _ticketState = MutableStateFlow<List<Ticket>>(emptyList())
//	val ticketState: StateFlow<List<Ticket>> = _ticketState
//
//	fun addTicket(ticket: Ticket) {
//		if (!_ticketState.value.contains(ticket)) {
//			_ticketState.value = _ticketState.value + ticket
//		}
//	}
//}

class TicketViewModel : ViewModel() {
	private val _ticketState = MutableStateFlow<List<Ticket>>(emptyList())
	val ticketState: StateFlow<List<Ticket>> = _ticketState

	private val ticketApi: TicketApi by lazy {
		RetrofitClient.instance.create(TicketApi::class.java)
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
				} else {
					// Handle API error
					// For example, log the error or show a message to the user
				}
			} catch (e: Exception) {
				// Handle exception (network error, etc.)
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
				} else {
					// Handle API error
					// For example, log the error or show a message to the user
				}
			} catch (e: Exception) {
				// Handle exception (network error, etc.)
			}
		}
	}
}
