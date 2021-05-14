package com.cmdv.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cmdv.data.entity.FavoriteCharacterRoomEntity
import com.cmdv.data.source.dao.FavoriteCharactersDao

@Database(entities = [FavoriteCharacterRoomEntity::class], version = 2)
abstract class FavouriteCharactersRoomDataBase : RoomDatabase() {
    abstract val favoriteCharactersDao: FavoriteCharactersDao

    companion object {
        @Volatile
        private var INSTANCE: FavouriteCharactersRoomDataBase? = null

        fun getInstance(context: Context): FavouriteCharactersRoomDataBase {
            synchronized(this) {
                var instance: FavouriteCharactersRoomDataBase? = INSTANCE
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            FavouriteCharactersRoomDataBase::class.java,
                            "favourite-characters-room-database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                }
                return instance
            }
        }
    }
}