package com.kuliah.vanilamovie.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kuliah.vanilamovie.domain.model.movie.Movie
import com.kuliah.vanilamovie.domain.model.movie.MovieDetail
import com.kuliah.vanilamovie.domain.usecases.movies.GetMovieDetailsUseCase
import com.kuliah.vanilamovie.domain.usecases.movies.GetNowPlayingMoviesUseCase
import com.kuliah.vanilamovie.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SeatScreenViewModel @AssistedInject constructor(
	private val getMovieDetailUseCase: GetMovieDetailsUseCase,
	private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,

	@Assisted private val id: Int
) : ViewModel() {

	private val _movie = MutableStateFlow<Resource<MovieDetail>>(Resource.Loading)
	val movie = _movie.asStateFlow()

	private val _nowPlayingMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
	val nowPlayingMovies = _nowPlayingMovies.asStateFlow()

	init {
		getNowPlayingMovies()
	}

	fun getNowPlayingMovies() {
		viewModelScope.launch {
			getNowPlayingMoviesUseCase.invoke()
				.cachedIn(viewModelScope)
				.collectLatest {
					_nowPlayingMovies.value = it
				}
		}
	}

}

@Suppress("UNCHECKED_CAST")
class SeatScreenViewModelFactory(
	private val id: Int,
	private val assistedFactory: SeatScreenViewModelAssistedFactory
) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return assistedFactory.create( id ) as T
	}
}

@AssistedFactory
interface SeatScreenViewModelAssistedFactory {
	fun create( id: Int ) : SeatScreenViewModel
}