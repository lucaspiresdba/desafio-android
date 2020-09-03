package br.com.lucaspires.data.source.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contactEntity")
data class ContactEntity(
    @PrimaryKey
    val id: Int,
    val img: String,
    val name: String,
    val username: String
)