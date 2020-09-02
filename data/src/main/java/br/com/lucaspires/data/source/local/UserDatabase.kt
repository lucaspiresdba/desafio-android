package br.com.lucaspires.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.lucaspires.data.source.model.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
}