package com.probeaufgabe.catapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BreedEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CatDatabase : RoomDatabase() {

    abstract fun breedDao(): BreedDao
}