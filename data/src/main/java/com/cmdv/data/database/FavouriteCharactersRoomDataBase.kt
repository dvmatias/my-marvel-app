package com.cmdv.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cmdv.data.entity.FavouriteCharacterRoomEntity
import com.cmdv.data.source.dao.FavouriteCharactersDao

@Database(entities = [FavouriteCharacterRoomEntity::class], version = 1)
abstract class FavouriteCharactersRoomDataBase : RoomDatabase() {
    abstract val favouriteCharactersDao: FavouriteCharactersDao

    companion object {
        @Volatile
        private var INSTANCE: FavouriteCharactersRoomDataBase? = null

        fun getInstance(context: Context): FavouriteCharactersRoomDataBase {
            synchronized(this) {
                var instance: FavouriteCharactersRoomDataBase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            FavouriteCharactersRoomDataBase::class.java,
                            "favourite-characters-room-database")
                            .fallbackToDestructiveMigration()
                            .build()
                }
                return instance
            }
        }
    }
}