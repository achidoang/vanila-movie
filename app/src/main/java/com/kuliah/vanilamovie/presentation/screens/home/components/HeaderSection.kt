package com.kuliah.vanilamovie.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HeaderSection(
	modifier: Modifier = Modifier,
	onClick: (Boolean) -> Unit,
	themeMode: Boolean,
	infoIconClick: () -> Unit,
) {
	Row(
		modifier
			.fillMaxSize()
			.padding(start = 10.dp, end = 10.dp, top = 10.dp),
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		ThemeSwitcher(
			size = 30.dp,
			padding = 5.dp,
			onClick = {
				onClick(it)
			},
			themeMode = themeMode,
			modifier = Modifier.padding(top = 8.dp) // Mengatur padding top
		)
		IconButton(onClick = {
			infoIconClick()
		}) {
			Icon(
				imageVector = Icons.Default.AccountCircle,
				contentDescription = "Theme Icon",
				Modifier.size(28.dp)
			)
		}
	}
}
