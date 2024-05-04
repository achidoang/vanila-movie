package com.kuliah.vanilamovie.di

import com.kuliah.vanilamovie.data.remote.services.ItunesApi
import com.kuliah.vanilamovie.data.remote.services.MovieApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [MovieApiModule::class, ItunesAppModule::class] )
interface AppComponent {
	fun getMovieApi(): MovieApi
	fun getItunes(): ItunesApi
}