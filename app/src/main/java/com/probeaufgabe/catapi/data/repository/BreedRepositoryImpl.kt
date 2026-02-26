package com.probeaufgabe.catapi.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.testcode.data.remote.BreedRemoteMediator
import com.probeaufgabe.catapi.data.local.CatDatabase
import com.probeaufgabe.catapi.data.local.toDomain
import com.probeaufgabe.catapi.data.mapper.toDomainModel
import com.probeaufgabe.catapi.data.remote.CatApi
import com.probeaufgabe.catapi.domain.model.Breed
import com.probeaufgabe.catapi.domain.repository.BreedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BreedRepositoryImpl(
    private val db: CatDatabase,
    private val api: CatApi
) : BreedRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedBreeds(sortByWeight: Boolean): Flow<PagingData<Breed>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = BreedRemoteMediator(api, db),
            pagingSourceFactory = {
                if (sortByWeight) db.breedDao().getBreedsSortedByWeightPaged()
                else db.breedDao().getBreedsPaged()
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomainModel() }
        }
    }

    override fun getBreedById(id: String): Flow<Breed?> {
        return db.breedDao().getBreedById(id).map { entity ->
            entity?.toDomain()
        }
    }

    override suspend fun voteBreed(breedId: String, voteChange: Int) {
        db.breedDao().updateVoteScore(breedId, voteChange)
    }
}