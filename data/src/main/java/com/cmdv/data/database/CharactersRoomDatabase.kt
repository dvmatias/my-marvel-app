package com.cmdv.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cmdv.data.entity.CharacterRoomEntity
import com.cmdv.data.source.dao.CharactersDao

@Database(entities = [ CharacterRoomEntity::class], version = 2)
abstract class CharactersRoomDatabase : RoomDatabase() {
    abstract val charactersDao: CharactersDao

    companion object {
        @Volatile
        private var INSTANCE: CharactersRoomDatabase? = null

        fun getInstance(context: Context): CharactersRoomDatabase {
            synchronized(this) {
                var instance: CharactersRoomDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CharactersRoomDatabase::class.java,
                        "characters-room-database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }

}