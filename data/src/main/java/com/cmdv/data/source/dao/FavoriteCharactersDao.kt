package com.cmdv.data.source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cmdv.data.entity.FavoriteCharacterRoomEntity

@Dao
interface FavoriteCharactersDao {
    @Query("SELECT * FROM `favorite-characters-room-database`")
    fun getAll(): List<FavoriteCharacterRoomEntity>

    @Query("SELECT * FROM `favorite-characters-room-database` WHERE characterId IN (:id)")
    fun getById(id: Int): FavoriteCharacterRoomEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: FavoriteCharacterRoomEntity): Long

    @Query("DELETE FROM `favorite-characters-room-database` WHERE characterId IN (:id)")
    fun delete(id: Int): Int

    @Query("DELETE FROM `favorite-characters-room-database`")
    fun deleteAll()
}