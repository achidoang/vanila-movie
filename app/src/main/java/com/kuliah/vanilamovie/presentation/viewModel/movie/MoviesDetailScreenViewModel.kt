package com.kuliah.vanilamovie.presentation.viewModel.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kuliah.vanilamovie.domain.model.movie.MovieDetail
import com.kuliah.vanilamovie.domain.usecases.movies.GetMovieDetailsUseCase
import com.kuliah.vanilamovie.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieDetailScreenViewModel @AssistedInject constructor(
	private val getMovieDetailUseCase: GetMovieDetailsUseCase,
	@Assisted private val id: Int
) : ViewModel() {

	private val _movie = MutableStateFlow<Resource<MovieDetail>>(Resource.Loading)
	val movie = _movie.asStateFlow()

	init {
		getMovieDetail()
	}

	fun getMovieDetail() {
		Resource.Loading
		viewModelScope.launch {
			getMovieDetailUseCase.invoke(id)
				.catch {
					_movie.value = Resource.Error( it.localizedMessage!! )
				}
				.collectLatest {
					_movie.value = Resource.Success(it)
				}
		}
	}

}

@Suppress("UNCHECKED_CAST")
class MovieDetailScreenViewModelFactory(
	private val id: Int,
	private val assistedFactory: MovieDetailScreenViewModelAssistedFactory
) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return assistedFactory.create( id ) as T
	}
}

@AssistedFactory
interface MovieDetailScreenViewModelAssistedFactory {
	fun create( id: Int ) : MovieDetailScreenViewModel
}