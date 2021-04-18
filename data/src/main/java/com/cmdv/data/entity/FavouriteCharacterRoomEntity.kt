package com.cmdv.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite-characters-room-database")
data class FavouriteCharacterRoomEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long?,
    @ColumnInfo(name = "characterId") val characterId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String
)