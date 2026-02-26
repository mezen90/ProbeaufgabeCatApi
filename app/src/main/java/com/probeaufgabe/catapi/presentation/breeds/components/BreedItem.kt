package com.probeaufgabe.catapi.presentation.breeds.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.probeaufgabe.catapi.domain.model.Breed

@Composable
fun BreedItem(
    breed: Breed,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onVoteClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = breed.imageUrl,
                contentDescription = "Image of ${breed.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = breed.name, style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = "Origin: ${breed.origin}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Weight: ${breed.weightMetric} kg",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {

                    IconButton(onClick = { onVoteClick(1) }) {
                        Icon(Icons.Default.ThumbUp, contentDescription = "Upvote")
                    }

                    Text(
                        text = breed.voteScore.toString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    IconButton(onClick = { onVoteClick(-1) }) {
                        Icon(Icons.Default.ThumbDown, contentDescription = "Downvote")
                    }
                    IconButton(onClick = onFavoriteClick) {
                        Icon(
                            imageVector = if (breed.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Toggle Favorite",
                            tint = if (breed.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}