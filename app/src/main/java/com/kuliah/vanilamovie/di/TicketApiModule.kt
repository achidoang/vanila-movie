package com.kuliah.vanilamovie.di

import com.kuliah.vanilamovie.data.remote.services.TicketApi
import com.kuliah.vanilamovie.util.Constants.VANILA_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object RetrofitClient {

	val instance: Retrofit by lazy {
		Retrofit.Builder()
			.baseUrl(VANILA_BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}
}
