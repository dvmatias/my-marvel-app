package com.cmdv.data.source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cmdv.data.entity.CharacterRoomEntity
import com.cmdv.data.entity.FavoriteCharacterRoomEntity

@Dao
interface CharactersDao {

    @Query("SELECT * FROM `characters-room-database`")
    fun getAll(): List<CharacterRoomEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characters: List<CharacterRoomEntity>)


    @Query("SELECT * FROM `characters-room-database`  WHERE characterId IN (:ids)")
    fun getById(ids: List<Int>): List<CharacterRoomEntity>


}