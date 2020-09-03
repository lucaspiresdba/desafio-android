package br.com.lucaspires.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.lucaspires.data.source.model.ContactEntity

@Database(entities = [ContactEntity::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun userDao(): ContactDAO
}