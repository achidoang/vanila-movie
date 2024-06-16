package com.kuliah.vanilamovie.presentation.screens.ticket


import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kuliah.vanilamovie.domain.model.ticket.Ticket
import com.kuliah.vanilamovie.presentation.navigation.Route
import com.kuliah.vanilamovie.presentation.viewModel.ticket.TicketViewModel
import com.kuliah.vanilamovie.util.formatRupiah
import com.kuliah.vanilamovie.util.loadTicketsFromSharedPreferences

@Composable
fun TicketScreen(ticketViewModel: TicketViewModel, navController: NavController) {
	val ticketState by ticketViewModel.ticketState.collectAsState()

	// Call loadTicketsFromApi when the TicketScreen composable is first launched
	LaunchedEffect(true) {
		ticketViewModel.loadTicketsFromApi()
	}

	// Navigate back to the home screen when back button is pressed
	BackHandler {
		navController.navigate(Route.Home.destination) {
			popUpTo(Route.Home.destination) { inclusive = true }
		}
	}

	Scaffold(
		content = { it
			if (ticketState.isEmpty()) {
				Box(
					modifier = Modifier.fillMaxSize(),
					contentAlignment = Alignment.Center
				) {
					Text(
						text = "No tickets available",
						style = MaterialTheme.typography.bodyLarge,
						color = Color.DarkGray
					)
				}
			} else {
				LazyColumn(
					modifier = Modifier.padding(horizontal = 16.dp),
					contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
				) {
					items(ticketState) { ticket ->
						TicketItem(ticket)
						Spacer(modifier = Modifier.height(16.dp))
					}
				}
			}
		}
	)
}

@Composable
fun TicketItem(ticket: Ticket) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.clip(shape = RoundedCornerShape(16.dp))
			.clickable { /* Handle click event */ },
		shape = RoundedCornerShape(16.dp),
	) {
		Column(
			modifier = Modifier.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			Text(
				text = ticket.movieTitle,
				style = MaterialTheme.typography.titleMedium,
				color = Color.Black
			)
			Text(
				text = "Price: ${formatRupiah(ticket.price)}",
				style = MaterialTheme.typography.labelMedium,
				color = Color.Black
			)
			Text(
				text = "Time: ${ticket.time}",
				style = MaterialTheme.typography.labelSmall,
				color = Color.Black
			)
			Text(
				text = "Date: ${ticket.date}",
				style = MaterialTheme.typography.bodySmall,
				color = Color.Black
			)
			Text(
				text = "Seats: ${ticket.seats.joinToString(", ")}",
				style = MaterialTheme.typography.bodySmall,
				color = Color.Black
			)
		}
	}
}
