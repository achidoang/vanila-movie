package com.kuliah.vanilamovie.presentation.viewModel.genres

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuliah.vanilamovie.domain.model.valueObjects.Genre
import com.kuliah.vanilamovie.domain.usecases.movies.GetMoviesGenreUseCase
import com.kuliah.vanilamovie.domain.usecases.shows.GetShowsGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
	private val getMoviesGenreUseCase: GetMoviesGenreUseCase,
	private val getShowsGenresUseCase: GetShowsGenresUseCase
) : ViewModel() {

	private val _moviesGenres = MutableStateFlow<List<Genre>>(emptyList())
	val moviesGenres = _moviesGenres.asStateFlow()

	private val _showsGenres = MutableStateFlow<List<Genre>>(emptyList())
	val showsGenres = _showsGenres.asStateFlow()


	var error by mutableStateOf("")

	init {
		getMovieGenres()
		getShowsGenres()
	}

	fun getMovieGenres() {
		viewModelScope.launch {
			getMoviesGenreUseCase.invoke()
				.catch {
					error = it.localizedMessage!!
				}
				.collectLatest {
					_moviesGenres.value = it
				}
		}
	}

	fun getShowsGenres() {
		viewModelScope.launch {
			getShowsGenresUseCase.invoke()
				.catch {
					error = it.localizedMessage!!
				}
				.collectLatest {
					_showsGenres.value = it
				}
		}
	}
}