package com.kuliah.vanilamovie.presentation.screens.ticket


import android.content.Context
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kuliah.vanilamovie.domain.model.ticket.Ticket
import com.kuliah.vanilamovie.presentation.navigation.Route
import com.kuliah.vanilamovie.presentation.viewModel.ticket.TicketViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TicketScreen(
	ticketViewModel: TicketViewModel,
	navController: NavController,
	themeMode: Boolean
) {
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
		content = {
			it
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
						TicketItem(ticket, themeMode)
						Spacer(modifier = Modifier.height(16.dp))
					}
				}
			}
		}
	)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TicketItem(ticket: Ticket, themeMode: Boolean) {
	val dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy", Locale("en", "EN"))
	val formattedDate = LocalDate.parse(ticket.date).format(dateFormatter)

	Card(
		modifier = Modifier
			.fillMaxWidth()
			.clip(shape = RoundedCornerShape(16.dp))
			.clickable { /* Handle click event */ },
		shape = RoundedCornerShape(16.dp),
		colors = CardDefaults.cardColors(
			containerColor =
			if (themeMode) Color(0xFF1A2C50)
			else Color(0xffECF2FF)
		)
	) {
		Column(
			modifier = Modifier.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			Text(
				text = ticket.movieTitle,
				style = MaterialTheme.typography.titleLarge,
				fontWeight = FontWeight.Bold,
				color = MaterialTheme.colorScheme.onBackground
			)
			Row(verticalAlignment = Alignment.CenterVertically) {
				Icon(
					modifier = Modifier.size(20.dp),
					imageVector = Icons.Default.LocationOn,
					contentDescription = "Location",
					tint = MaterialTheme.colorScheme.onBackground
				)
				Spacer(modifier = Modifier.width(4.dp))
				Text(
					text = "Vanila Cinema",
					style = MaterialTheme.typography.labelMedium,
					color = MaterialTheme.colorScheme.onBackground
				)
			}

			Row(verticalAlignment = Alignment.CenterVertically) {
				Icon(
					modifier = Modifier.size(20.dp),
					imageVector = Icons.Default.MovieCreation,
					contentDescription = "Ticket",
					tint = MaterialTheme.colorScheme.onBackground
				)
				Spacer(modifier = Modifier.width(4.dp))
				Text(
					text = "Ticket (${ticket.seats.size})",
					style = MaterialTheme.typography.labelMedium,
					color = MaterialTheme.colorScheme.onBackground
				)
			}

			Row(verticalAlignment = Alignment.CenterVertically) {
				Text(
					text = formattedDate,
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onBackground
				)
				Text(
					text = ", ${ticket.time}",
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onBackground
				)
			}
			Row(
				modifier = Modifier.fillMaxWidth(),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween
			) {

				Text(
					text = "Seats: ${ticket.seats.joinToString(", ")}",
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onBackground
				)
				Text(
					text = "Berhasil",
					style = MaterialTheme.typography.bodySmall,
					fontWeight = FontWeight.Bold,
					color = if (themeMode) Color.Yellow
					else Color(0xFF1A2C50),
					textAlign = TextAlign.End
				)
			}
		}
	}
}

