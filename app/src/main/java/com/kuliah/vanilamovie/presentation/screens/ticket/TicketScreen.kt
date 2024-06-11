package com.kuliah.vanilamovie.presentation.screens.ticket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kuliah.vanilamovie.presentation.navigation.Route

@Composable
fun TicketScreen(
	movieTitle: String?,
	selectedSeats: String?,
	totalPrice: Int?,
	selectedTime: String?,
	selectedDate: String?,
	navController: NavHostController
) {
	if (movieTitle == null || selectedSeats == null || totalPrice == null || selectedTime == null || selectedDate == null) {
		// Tampilkan pesan kesalahan atau arahkan ke layar yang sesuai
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(16.dp),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text("No ticket data available. Please complete the checkout process.")
			Spacer(modifier = Modifier.height(20.dp))
			Button(onClick = { navController.navigate(Route.Home.destination) }) {
				Text("Go to Home")
			}
		}
	} else {
		// Tampilkan informasi tiket
		Scaffold(
			topBar = {
				TopAppBar(title = { Text("Ticket") })
			}
		) { padding ->
			Column(
				modifier = Modifier
					.padding(padding)
					.fillMaxSize()
					.padding(16.dp),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text("Movie Title: $movieTitle", style = MaterialTheme.typography.h6)
				Text("Seats: $selectedSeats", style = MaterialTheme.typography.body1)
				Text("Price: Rp $totalPrice", style = MaterialTheme.typography.body1)
				Text("Time: $selectedTime", style = MaterialTheme.typography.body1)
				Text("Date: $selectedDate", style = MaterialTheme.typography.body1)
			}
		}
	}
}
