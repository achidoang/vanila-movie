package com.kuliah.vanilamovie.presentation.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.kuliah.vanilamovie.presentation.theme.Black

@Composable
fun PageIndicator(
	modifier: Modifier = Modifier,
	currentPage: String,
	onIndicatorClick: (String) -> Unit
) {
	Row(
		modifier = modifier
			.padding(16.dp)
			.fillMaxWidth(),
		horizontalArrangement = Arrangement.Center
	) {
		val pages = listOf("Movies", "Shows")
		pages.forEach { page ->
			Text(
				text = page,
				modifier = Modifier
					.padding(horizontal = 8.dp)
					.clickable { onIndicatorClick(page) }
					.then(
						if (page == currentPage) Modifier.underline() else Modifier
					),
				style = if (page == currentPage) {
					MaterialTheme.typography.bodyLarge
				} else {
					MaterialTheme.typography.bodyLarge
				}
			)
		}
	}
}


fun Modifier.underline() = this.then(
	Modifier.drawBehind {
		val strokeWidth = 2.dp.toPx()
		val y = size.height - strokeWidth / 2
		drawLine(
			color = Color(0xFFCCC2DC),
			start = Offset(0f, y),
			end = Offset(size.width, y),
			strokeWidth = strokeWidth
		)
	}
)
