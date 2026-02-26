package com.probeaufgabe.catapi.presentation.breeds

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.getValue
import com.probeaufgabe.catapi.presentation.breeds.components.BreedItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedListScreen(
    onNavigateToDetail: (String) -> Unit,
    viewModel: BreedViewModel = koinViewModel()
) {
    val breeds = viewModel.breeds.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cat Breeds") },
                actions = {
                    val isSorted by viewModel.sortByWeight.collectAsState()

                    IconButton(onClick = { viewModel.toggleSort() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Sort,
                            contentDescription = "Nach Gewicht sortieren",
                            tint = if (isSorted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (breeds.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (breeds.loadState.refresh is LoadState.Error) {
                val error = (breeds.loadState.refresh as LoadState.Error).error
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Fehler: ${error.localizedMessage}",
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { breeds.retry() }) {
                        Text("Erneut versuchen")
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        count = breeds.itemCount,
                        key = { index -> breeds[index]?.id ?: index }
                    ) { index ->
                        val breed = breeds[index]
                        if (breed != null) {
                            BreedItem(
                                breed = breed,
                                onClick = { onNavigateToDetail(breed.id) },
                                onFavoriteClick = {
                                    viewModel.onToggleFavorite(
                                        //TODO
                                    )
                                },
                                onVoteClick = { voteChange ->
                                    viewModel.onVote(
                                        breed.id,
                                        voteChange
                                    )
                                }
                            )
                            Text(text = breed.name, modifier = Modifier.padding(16.dp))
                        }
                    }
                    if (breeds.loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    if (breeds.loadState.append is LoadState.Error) {
                        item {
                            Button(
                                onClick = { breeds.retry() },
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Text("Mehr laden fehlgeschlagen. Retry?")
                            }
                        }
                    }
                }
            }
        }
    }
}