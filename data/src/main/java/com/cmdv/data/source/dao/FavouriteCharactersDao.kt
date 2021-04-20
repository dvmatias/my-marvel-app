package com.cmdv.data.source.dao

import androidx.room.*
import com.cmdv.data.entity.FavouriteCharacterRoomEntity

@Dao
interface FavouriteCharactersDao {
    @Query("SELECT * FROM `favourite-characters-room-database`")
    fun getAll(): List<FavouriteCharacterRoomEntity>

    @Query("SELECT * FROM `favourite-characters-room-database` WHERE characterId IN (:id)")
    fun getById(id: Int): FavouriteCharacterRoomEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavourite(favouriteCharacter: FavouriteCharacterRoomEntity): Long

    @Delete
    fun delete(favouriteCharacter: FavouriteCharacterRoomEntity)
}