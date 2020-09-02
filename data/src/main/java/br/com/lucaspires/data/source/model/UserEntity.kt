package br.com.lucaspires.data.source.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userEntity")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val img: String,
    val name: String,
    val username: String
)