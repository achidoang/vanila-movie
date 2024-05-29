package com.kuliah.vanilamovie.presentation.screens.seat

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kuliah.vanilamovie.presentation.screens.bioskop.DateComp
import com.kuliah.vanilamovie.presentation.screens.bioskop.TimeComp
import com.kuliah.vanilamovie.presentation.theme.LightGreen
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SeatSelectorScreen(
//	navController: NavController
) {

	val today = LocalDate.now()
	val dateScrollState = rememberScrollState()
	val timeScrollState = rememberScrollState()

	val selectedData = remember {
		mutableStateOf<LocalDate?>(null)
	}

	val selectedTime = remember {
		mutableStateOf<String?>(null)
	}

	val selectedSeat = remember {
		mutableStateListOf<String>()
	}

	Scaffold(
		//		backgroundColor = LightGreen
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.fillMaxSize()
		) {
			Row(
				modifier = Modifier.padding(
					horizontal = 16.dp, vertical = 8.dp
				),
				verticalAlignment = Alignment.CenterVertically,
			) {
//				IconButton(onClick = {
//					navController.popBackStack()
//				}) {
//					Icon(Icons.Filled.ArrowBack, contentDescription = "Back Button")
//				}
//				Spacer(modifier = Modifier.width(16.dp))
				Text(text = "Select Seat", style = MaterialTheme.typography.titleSmall)
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
					style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
				)
			}

			// Seat mapping
			for (i in 1..6) {
				Row(
					modifier = Modifier.align(Alignment.CenterHorizontally)
				) {
					for (j in 1..8) {
						val seatNumber = "${(64 + i).toChar()}$j"
						SeatComp(
							isEnabled = i != 6,
							isSelected = selectedSeat.contains(seatNumber),
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
				color = Color.White,
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
					)

					Row(
						modifier = Modifier.horizontalScroll(dateScrollState),
						horizontalArrangement = Arrangement.spacedBy(8.dp)
					) {
						for (i in 0..14) {
							val date = today.plusDays(i.toLong())
							DateComp(
								date = date, isSelected = selectedData.value == date
							) {
								selectedData.value = it
							}
						}
					}

					Row(
						modifier = Modifier.horizontalScroll(timeScrollState),
						horizontalArrangement = Arrangement.spacedBy(8.dp)
					) {
						for (i in 0..22 step 2) {
							val time = "$i:00"
							TimeComp(
								time = time, isSelected = selectedTime.value == time
							) {
								selectedTime.value = it
							}
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
	seatNumber: String = "",
	onClick: (Boolean, String) -> Unit = { _, _ -> },
) {
	val seatColor = when {
		!isEnabled -> Color.Gray
		isSelected -> Color.Yellow
		else -> Color.White
	}

	val textColor = when {
		isSelected -> Color.White
		else -> Color.Black
	}

	Box(modifier = Modifier
		.size(32.dp)
		.border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
		.clip(RoundedCornerShape(8.dp))
		.background(color = seatColor)
		.clickable {
			onClick(isSelected, seatNumber);
		}
		.padding(8.dp), contentAlignment = Alignment.Center) {
		Text(
			seatNumber,
			style = MaterialTheme.typography.labelSmall.copy(color = textColor),
		)
	}
}