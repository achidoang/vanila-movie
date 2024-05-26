package com.kuliah.vanilamovie.presentation.screens.bioskop

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.itemContentType
import com.kuliah.vanilamovie.domain.model.movie.MovieDetail
import com.kuliah.vanilamovie.presentation.screens.search.components.SearchScreenMovieItem
import com.kuliah.vanilamovie.presentation.theme.SeaGreen
import com.kuliah.vanilamovie.presentation.theme.VanilaMovieTheme
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BioskopScreen(
	modifier: Modifier = Modifier,
	onDateSelected: (String) -> Unit = {},
	onTimeSelected: (String) -> Unit = {}
) {
	val today = remember { LocalDate.now() }
	val dates = remember { (0..2).map { today.plusDays(it.toLong()) } }

	Column(
		modifier = modifier
			.fillMaxSize()
			.padding(16.dp)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth(),
			horizontalArrangement = Arrangement.Start,

			) {
			dates.forEach { date ->
				Button(
					onClick = { onDateSelected(date.toString()) },
					modifier = Modifier.padding(end = 20.dp)
				) {
					Text(text = "${date.dayOfWeek.name.take(3)}, ${date.dayOfMonth}")
				}
			}
		}

		Spacer(modifier = Modifier.height(16.dp))

		Text(
			text = "Solo Paragon XXI",
			style = MaterialTheme.typography.titleSmall,
			fontWeight = FontWeight.Bold,
			modifier = Modifier.align(Alignment.Start)
		)

		Spacer(modifier = Modifier.height(20.dp))

		val showTimes = listOf("10:00", "12:30", "15:00", "17:30", "20:00")

		LazyHorizontalGrid(
			rows = GridCells.Fixed(2),
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.spacedBy(10.dp),
			contentPadding = PaddingValues(top = 15.dp)
		) {
			items(showTimes) { time ->
				Button(
					onClick = { onTimeSelected(time) },
					modifier = Modifier.padding(end = 20.dp, bottom = 10.dp)  // Atur tinggi tombol di sini
				) {
					Text(text = time, textAlign = TextAlign.Center)
				}
			}
		}
	}
}

@Composable
fun DetailDataMovie(movie: MovieDetail) {
	Column {
		if (movie.tagline != "") {
			Text(
				text = movie.tagline ?: "",
				modifier = Modifier
					.fillMaxWidth()
					.padding(bottom = 14.dp, start = 10.dp),
				fontStyle = FontStyle.Italic,
				fontWeight = FontWeight.Medium,
				fontSize = 17.sp
			)
		}

		if (!movie.overview.isNullOrEmpty()) {


			Text(
				text = "Overview",
				Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
				fontSize = 18.sp,
				fontWeight = FontWeight.Medium,
				color = SeaGreen
			)
			Text(
				text = movie.overview,
				modifier = Modifier
					.fillMaxWidth()
					.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
				color = MaterialTheme.colorScheme.onBackground
			)
		}
	}
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun BioskopScreenPreview() {
	VanilaMovieTheme {
		BioskopScreen(
			onDateSelected = { selectedDate ->
				println("Selected date: $selectedDate")
			},
			onTimeSelected = { selectedTime ->
				println("Selected time: $selectedTime")
			}
		)

	}
}