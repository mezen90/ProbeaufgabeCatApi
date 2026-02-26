package com.example.testcode.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.probeaufgabe.catapi.data.local.BreedEntity
import com.probeaufgabe.catapi.data.local.CatDatabase
import com.probeaufgabe.catapi.data.mapper.toEntity
import com.probeaufgabe.catapi.data.remote.CatApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BreedRemoteMediator(
    private val api: CatApi,
    private val db: CatDatabase
) : RemoteMediator<Int, BreedEntity>() {

    private var pageIndex = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BreedEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    pageIndex = 0
                    pageIndex
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    pageIndex++
                    pageIndex
                }
            }

            val response = api.getBreeds(page = page, limit = state.config.pageSize)
            val endOfPaginationReached = response.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.breedDao().clearUnmodifiedBreeds()
                }
                val entities = response.map { it.toEntity() }
                db.breedDao().insertBreeds(entities)
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.breedDao().clearUnmodifiedBreeds()
                }
                val entities = response.map { it.toEntity() }
                db.breedDao().insertBreeds(entities)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}