package br.com.lucaspires.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.lucaspires.data.source.model.UserEntity
import io.reactivex.Single

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(userEntity: List<UserEntity>)

    @Query("SELECT * FROM userEntity")
    fun getUsersLocal(): Single<List<UserEntity>>
}
