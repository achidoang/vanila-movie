package com.kuliah.vanilamovie.presentation.screens.home.components

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AlertDialog(
	dialogState: Boolean,
	onOkClicked: () -> Unit,
	onDismiss: () -> Unit
) {

	if ( dialogState ) {
		androidx.compose.material3.AlertDialog(
			title = {
				Text(text = "Movie Preview App!")
			},
			text = {
				Text("Developed by Engineer Fred")
			},
			confirmButton = {
				OutlinedButton(
					onClick = {
						onOkClicked()
					}) {
					Text("Ok")
				}
			},
			onDismissRequest = {
				onDismiss()
			}
		)
	}
}