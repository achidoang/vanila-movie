package com.kuliah.vanilamovie.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplaneTicket
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarItem(
	val label : String = "",
	val icon : ImageVector = Icons.Filled.Home,
	val route : String = "",
) {

	fun getBottomNavigationItems() : List<BottomBarItem> {
		return listOf(
			BottomBarItem(
				label = "Home",
				icon = Icons.Filled.Home,
				route = Route.Home.destination
			),
			BottomBarItem(
				label = "Search",
				icon = Icons.Filled.Search,
				route = Route.Pager.destination
			),
			BottomBarItem(
				label = "Ticket",
				icon = Icons.Filled.MovieFilter,
				route = Route.Ticket.destination
			)
		)
	}
}