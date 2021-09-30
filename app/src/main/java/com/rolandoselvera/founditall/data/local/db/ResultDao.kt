package com.rolandoselvera.founditall.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resultDTO: List<ResultDTO>)

    @Query("DELETE FROM Results")
    suspend fun deleteAll()

    @Query("SELECT * FROM Results")
    fun getAllResults(): Flow<List<ResultDTO>>
}