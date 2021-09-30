package com.rolandoselvera.founditall.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Results")
data class ResultDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "Name")
    val name: String?,
    @ColumnInfo(name = "Type")
    val type: String?,
    @ColumnInfo(name = "wTeaser")
    val wikiTeaser: String?,
    @ColumnInfo(name = "yUrl")
    val youTubeUrl: String?,
    @ColumnInfo(name = "yID")
    val youTubeId: String?
)
