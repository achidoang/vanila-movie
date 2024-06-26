package com.kuliah.vanilamovie.presentation.screens.seat

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kuliah.vanilamovie.domain.model.movie.MovieDetail
import com.kuliah.vanilamovie.domain.model.ticket.Ticket
import com.kuliah.vanilamovie.presentation.navigation.Route
import com.kuliah.vanilamovie.presentation.screens.bioskop.DateComp
import com.kuliah.vanilamovie.presentation.screens.bioskop.TimeComp
import com.kuliah.vanilamovie.presentation.viewModel.movie.MovieDetailScreenViewModel
import com.kuliah.vanilamovie.presentation.viewModel.movie.MovieDetailScreenViewModelAssistedFactory
import com.kuliah.vanilamovie.presentation.viewModel.movie.MovieDetailScreenViewModelFactory
import com.kuliah.vanilamovie.presentation.viewModel.ticket.TicketViewModel
import com.kuliah.vanilamovie.util.formatRupiah
import com.kuliah.vanilamovie.util.saveTicketsToSharedPreferences
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SeatSelectorScreen(
	navController: NavHostController,
	movieTitle: String,
	ticketViewModel: TicketViewModel,
) {
	// Define data structure to store ticket reservations
	val tickets = ticketViewModel.ticketState.collectAsState().value

	val today = LocalDate.now()
	val dateScrollState = rememberScrollState()
	val timeScrollState = rememberScrollState()

	val selectedDate = remember {
		mutableStateOf<LocalDate?>(null)
	}

	val selectedTime = remember {
		mutableStateOf<String?>(null)
	}

	val selectedSeat = remember {
		mutableStateListOf<String>()
	}

	var showDialog by remember {
		mutableStateOf(false)
	}

	var showIncompleteSelectionAlert by remember {
		mutableStateOf(false)
	}

	var showSeatNotAvailableAlert by remember {
		mutableStateOf(false)
	}

	val pricePerSeat = 30000
	val totalPrice = selectedSeat.size * pricePerSeat

	Scaffold(
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.fillMaxSize()
		) {
			Column(
				modifier = Modifier.padding(
					horizontal = 16.dp, vertical = 8.dp
				),
				verticalArrangement = Arrangement.Center
			) {
				Text(text = "Select Seat", style = MaterialTheme.typography.titleSmall)
				Text(text = "${movieTitle}", style = MaterialTheme.typography.titleSmall)
			}

			Box(
				modifier = Modifier
					.align(Alignment.CenterHorizontally)
					.padding(bottom = 48.dp, top = 8.dp)
					.background(color = Yellow)
					.fillMaxWidth(0.5f),
				contentAlignment = Alignment.Center,
			) {
				Text(
					text = "Screen",
					style = MaterialTheme.typography.bodyMedium.copy(color = Black)
				)
			}

			// Seat mapping
			for (i in 1..6) {
				Row(
					modifier = Modifier.align(Alignment.CenterHorizontally)
				) {
					for (j in 1..8) {
						val seatNumber = "${(64 + i).toChar()}$j"
						// Membuat kursi gray jika sudah dipesan
						val isCheckedOut = tickets.any { ticket ->
							ticket.date == selectedDate.value.toString() &&
									ticket.time == selectedTime.value &&
									ticket.movieTitle == movieTitle &&
									ticket.seats.contains(seatNumber)
						}

						SeatComp(
							isEnabled = i != 6,
							isSelected = selectedSeat.contains(seatNumber),
							isCheckedOut = isCheckedOut,
							seatNumber = seatNumber
						) { selected, seat ->
							if (selected) {
								selectedSeat.remove(seat)
							} else {
								selectedSeat.add(seat)
							}
						}

						if (j != 8) Spacer(modifier = Modifier.width(if (j == 4) 16.dp else 8.dp))
					}
				}
				Spacer(modifier = Modifier.height(8.dp))
			}
			Spacer(modifier = Modifier.height(24.dp))

			// Info seat indicator
			Row(
				modifier = Modifier.align(Alignment.CenterHorizontally),
				verticalAlignment = Alignment.CenterVertically
			) {

				SeatComp(isEnabled = false)
				Spacer(modifier = Modifier.width(4.dp))
				Text(
					"Reserved",
					style = MaterialTheme.typography.labelSmall,
				)

				Spacer(modifier = Modifier.width(16.dp))

				SeatComp(isEnabled = true, isSelected = true)
				Spacer(modifier = Modifier.width(4.dp))
				Text(
					"Selected",
					style = MaterialTheme.typography.labelSmall,
				)

				Spacer(modifier = Modifier.width(16.dp))

				SeatComp(isEnabled = true, isSelected = false)
				Spacer(modifier = Modifier.width(4.dp))
				Text(
					"Available",
					style = MaterialTheme.typography.labelSmall,
				)
			}

			Spacer(modifier = Modifier.height(24.dp))
			Surface(
				modifier = Modifier
					.weight(1f)
					.fillMaxWidth(),
				color = Color(0xFF1A2C50),
				shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
			) {
				Column(
					modifier = Modifier
						.fillMaxSize()
						.padding(16.dp),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.SpaceBetween
				) {
					Text(
						text = "Select Seat",
						style = MaterialTheme.typography.labelMedium,
						color = White

					)

					Row(
						modifier = Modifier.horizontalScroll(dateScrollState),
						horizontalArrangement = Arrangement.spacedBy(8.dp)
					) {
						for (i in 0..6) {
							val date = today.plusDays(i.toLong())
							DateComp(
								date = date, isSelected = selectedDate.value == date
							) {
								selectedDate.value = it
							}
						}
					}

					Row(
						modifier = Modifier.horizontalScroll(timeScrollState),
						horizontalArrangement = Arrangement.spacedBy(8.dp)
					) {
						for (i in 9..22 step 2) {
							val time = "$i:00"
							TimeComp(
								time = time, isSelected = selectedTime.value == time
							) {
								selectedTime.value = it
							}
						}
					}

					Row(
						modifier = Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.SpaceBetween,
						verticalAlignment = Alignment.CenterVertically,
					) {
						Column(
							verticalArrangement = Arrangement.spacedBy(8.dp)
						) {
							Row {
								Text(
									text = "Price : ",
									style = MaterialTheme.typography.titleMedium,
									color = White
								)
								Text(
									text = formatRupiah(totalPrice),
									style = MaterialTheme.typography.titleMedium,
									color = White
								)

							}

						}

						Button(
							onClick = {
								if (selectedSeat.isEmpty() || selectedTime.value.isNullOrEmpty() || selectedDate.value == null) {
									showIncompleteSelectionAlert = true
								} else {
									// Check if the selected seats are available
									val selectedSeatsAvailable = selectedSeat.all { seat ->
										tickets.none {
											it.date == selectedDate.value.toString() &&
													it.time == selectedTime.value!! &&
													it.movieTitle == movieTitle &&
													it.seats.contains(seat)
										}
									}
									if (selectedSeatsAvailable) {
										// If seats are available, proceed with checkout
										showDialog = true
									} else {
										// If seats are not available, show a message
										showSeatNotAvailableAlert = true
									}
								}
							},
							modifier = Modifier
								.wrapContentWidth()
								.height(50.dp),
							colors = ButtonDefaults.buttonColors(
								Yellow
							),
							shape = RoundedCornerShape(32.dp),
						) {
							Text(text = "Check Out", color = Black)
						}

						// Dialog to show when selected seats are not available
						if (showSeatNotAvailableAlert) {
							AlertDialog(
								onDismissRequest = { showSeatNotAvailableAlert = false },
								title = { Text(text = "Perhatian") },
								text = { Text("Kursi yang Anda pilih sudah tidak tersedia. Silakan pilih kursi lain.") },
								confirmButton = {
									Button(
										onClick = { showSeatNotAvailableAlert = false }
									) {
										Text("OK")
									}
								}
							)
						}

						if (showDialog) {
							AlertDialog(
								modifier = Modifier.fillMaxWidth(),
								onDismissRequest = { showDialog = false },
								title = { Text(text = "CheckOut") },
								text = {
									val dateFormatter = DateTimeFormatter.ofPattern(
										"EEEE, dd MMM yyyy",
										Locale("en", "EN")
									)
									val formattedDate =
										LocalDate.parse(selectedDate.value.toString())
											.format(dateFormatter)
									Column(modifier = Modifier.fillMaxWidth()) {
										Text(
											text = "${movieTitle}",
											style = MaterialTheme.typography.titleMedium
										)
										Spacer(modifier = Modifier.height(12.dp))

										Row(horizontalArrangement = Arrangement.SpaceBetween) {
											Text(text = "${formattedDate ?: "Belum dipilih"},")
											Spacer(modifier = Modifier.width(8.dp))
											Text("${selectedTime.value ?: "Belum dipilih"}")
										}

										Row(
											modifier = Modifier.fillMaxWidth(),
											horizontalArrangement = Arrangement.SpaceBetween
										) {
											Box(modifier = Modifier.weight(1f)) {
												Text(
													text = if (selectedSeat.isEmpty()) {
														"Seats: Anda Belum memilih Kursi"
													} else {
														"Seats: ${selectedSeat.sorted().joinToString(", ")}"
													},
												)
											}
											Spacer(modifier = Modifier.width(8.dp))
											Text("(${selectedSeat.size} Ticket)", modifier = Modifier.wrapContentWidth(Alignment.End))
										}
										Row(horizontalArrangement = Arrangement.SpaceBetween) {
											Text("Total Payment : ")
											Text("${formatRupiah(totalPrice)}")

										}
									}
								},
								confirmButton = {
									Button(
										onClick = {
											showDialog = false
											val ticket = Ticket(
												movieTitle = movieTitle,
												price = totalPrice,
												time = selectedTime.value!!,
												date = selectedDate.value.toString(),
												seats = selectedSeat.toList(),
											)
											ticketViewModel.addTicket(ticket)

											navController.navigate(Route.Ticket.destination)
										},
										colors = ButtonDefaults.buttonColors(
											containerColor = Color(0xFF1A2C50),
											contentColor = Yellow,
										),
										modifier = Modifier
											.fillMaxWidth()
											.height(36.dp),
										shape = RoundedCornerShape(16.dp)
									) {
										Text("Buy Now")
									}
								},
								dismissButton = null, // Menghilangkan dismiss button
							)
						}

						if (showIncompleteSelectionAlert) {
							AlertDialog(
								onDismissRequest = { showIncompleteSelectionAlert = false },
								title = { Text(text = "Attention!") },
								text = { Text("You must select a seat, time, and date berfore proceeding.") },
								confirmButton = {
									Button(
										onClick = { showIncompleteSelectionAlert = false },
										colors = ButtonDefaults.buttonColors(
											containerColor = Color(0xFF1A2C50),
											contentColor = Yellow,
										),
										modifier = Modifier
											.fillMaxWidth()
											.height(36.dp),
										shape = RoundedCornerShape(16.dp)
									) {
										Text("OK")
									}
								}
							)
						}

					}
				}
			}
		}
	}
}



@Composable
fun SeatComp(
	isEnabled: Boolean = false,
	isSelected: Boolean = false,
	isCheckedOut: Boolean = false,
	seatNumber: String = "",
	onClick: (Boolean, String) -> Unit = { _, _ -> },
) {
	val seatColor = when {
		!isEnabled -> Gray
		isCheckedOut -> Gray
		isSelected -> Yellow
		else -> White
	}

	val textColor = when {
		isSelected -> DarkGray
		else -> Black
	}

	Box(
		modifier = Modifier
			.size(32.dp)
			.border(width = 1.dp, color = Gray, shape = RoundedCornerShape(8.dp))
			.clip(RoundedCornerShape(8.dp))
			.background(color = seatColor)
			.let {
				if (isEnabled && !isCheckedOut) it.clickable {
					onClick(
						isSelected,
						seatNumber
					)
				} else it
			}
			.padding(8.dp),
		contentAlignment = Alignment.Center
	) {
		Text(
			seatNumber,
			style = MaterialTheme.typography.labelSmall.copy(color = textColor),
		)
	}
}
