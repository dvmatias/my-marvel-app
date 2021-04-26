package com.cmdv.data.source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cmdv.data.entity.FavouriteCharacterRoomEntity

@Dao
interface FavouriteCharactersDao {
    @Query("SELECT * FROM `favourite-characters-room-database`")
    fun getAll(): List<FavouriteCharacterRoomEntity>

    @Query("SELECT * FROM `favourite-characters-room-database` WHERE characterId IN (:id)")
    fun getById(id: Int): FavouriteCharacterRoomEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favouriteCharacter: FavouriteCharacterRoomEntity): Long

    @Query("DELETE FROM `favourite-characters-room-database` WHERE characterId IN (:id)")
    fun delete(id: Int): Int

    @Query("DELETE FROM `favourite-characters-room-database`")
    fun deleteAll()
}