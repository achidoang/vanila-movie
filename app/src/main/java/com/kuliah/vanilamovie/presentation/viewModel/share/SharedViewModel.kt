package com.kuliah.vanilamovie.presentation.viewModel.share

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

	var moviePath by mutableStateOf("")
		private set

	fun putPosterPath(moviePath: String ) {
		this.moviePath = moviePath
	}

}