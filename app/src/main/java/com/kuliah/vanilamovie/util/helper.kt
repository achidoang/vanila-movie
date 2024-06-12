package com.kuliah.vanilamovie.util

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kuliah.vanilamovie.domain.model.ticket.Ticket
import com.kuliah.vanilamovie.util.Constants.BASE_IMAGE_URL
import com.kuliah.vanilamovie.util.Constants.BASE_IMAGE_URL_W500
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

fun displayOriginalImage(imagePath: String?) : String {
	return "${BASE_IMAGE_URL}$imagePath"
}

fun displayPosterImage( imagePath: String? ) : String {
	return "${BASE_IMAGE_URL_W500}$imagePath"
}

fun convertMinutesToHoursAndMinutes(minutes: Int): String {
	val hours = minutes / 60
	val remainingMinutes = minutes % 60
	return "$hours hr $remainingMinutes mins"
}

fun separateWithCommas(number: Long): String {
	val formatter: NumberFormat = DecimalFormat("#,###")
	return formatter.format(number)
}

fun convertToPercentage(figure: Double): String {
	val percentage = (figure * 10).toInt()
	return percentage.toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(releaseDate: String): String {
	val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
	val outputFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH)
	val date = LocalDate.parse(releaseDate, inputFormatter)
	return outputFormatter.format(date)
}

fun formatRupiah(amount: Int): String {
	val localeID = Locale("in", "ID")
	val numberFormat = NumberFormat.getCurrencyInstance(localeID)
	return numberFormat.format(amount)
}

// Simpan data tiket ke dalam shared preferences
fun saveTicketsToSharedPreferences(context: Context, tickets: List<Ticket>) {
	val sharedPreferences = context.getSharedPreferences("tickets", Context.MODE_PRIVATE)
	val editor = sharedPreferences.edit()
	val gson = Gson()
	val json = gson.toJson(tickets)
	editor.putString("tickets", json)
	editor.apply()
}
// Memuat data tiket dari shared preferences

fun loadTicketsFromSharedPreferences(context: Context): List<Ticket> {
	val sharedPreferences = context.getSharedPreferences("tickets", Context.MODE_PRIVATE)
	val gson = Gson()
	val json = sharedPreferences.getString("tickets", null)
	return if (json != null) {
		val type = object : TypeToken<List<Ticket>>() {}.type
		gson.fromJson(json, type)
	} else {
		emptyList()
	}
}






