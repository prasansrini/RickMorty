package com.rick.morty.feature_ram.components.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rick.morty.feature_ram.ui.theme.RickAction
import com.rick.morty.feature_ram.ui.theme.RickTextPrimary

data class DataPoint(
		val title: String, val description: String
)

@Composable
fun DataPointComponent(dataPoint: DataPoint) {
	Column {
		Text(
			text = dataPoint.title,
			fontSize = 14.sp,
			fontWeight = FontWeight.Bold,
			color = RickAction
		)
		Text(
			text = dataPoint.description,
			fontSize = 24.sp,
			color = RickTextPrimary
		)
	}
}

@Composable
fun DataPointEpisodeComponent(dataPoint: DataPoint) {
	Column(
		modifier = Modifier
			.border(
				width = 2.dp,
				color = RickAction,
				shape = RoundedCornerShape(size = 12.dp)
			)
			.padding(12.dp)
	) {
		Text(
			text = dataPoint.title,
			fontSize = 14.sp,
			fontWeight = FontWeight.Bold,
			color = RickAction
		)
		Text(
			text = dataPoint.description,
			fontSize = 24.sp,
			color = RickTextPrimary
		)
	}
}

@Preview
@Composable
fun DataPointPreview(modifier: Modifier = Modifier) {
	DataPointEpisodeComponent(
		DataPoint(
			"Last known location",
			"Location"
		)
	)
}