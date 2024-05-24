package com.rick.morty.feature_ram.components.common

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun CharacterImage(imageUrl: String, modifier: Modifier = Modifier) {
	SubcomposeAsyncImage(model = imageUrl,
		contentDescription = "Character image",
		modifier = modifier
			.fillMaxWidth()
			.aspectRatio(1f)
			.padding(12.dp)
			.clip(RoundedCornerShape(12.dp)),
		loading = { LoadingState() })
}