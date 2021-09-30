package com.rolandoselvera.founditall.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(resultDTO: ResultDTO)

    @Query("SELECT * FROM Results")
    fun getAllResults(): Flow<ResultDTO>
}