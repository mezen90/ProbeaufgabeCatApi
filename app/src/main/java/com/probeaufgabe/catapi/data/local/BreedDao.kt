package com.probeaufgabe.catapi.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {

    @Query("SELECT * FROM breeds")
    fun getBreedsPaged(): PagingSource<Int, BreedEntity>

    @Query("SELECT * FROM breeds ORDER BY averageWeightMetric ASC")
    fun getBreedsSortedByWeightPaged(): PagingSource<Int, BreedEntity>

    @Query("SELECT * FROM breeds WHERE id = :breedId")
    fun getBreedById(breedId: String): Flow<BreedEntity?>

    @Query("UPDATE breeds SET voteScore = voteScore + :voteChange WHERE id = :breedId")
    suspend fun updateVoteScore(breedId: String, voteChange: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBreeds(breeds: List<BreedEntity>)


    @Query("DELETE FROM breeds WHERE isFavorite = 0 AND voteScore = 0")
    suspend fun clearUnmodifiedBreeds()

}