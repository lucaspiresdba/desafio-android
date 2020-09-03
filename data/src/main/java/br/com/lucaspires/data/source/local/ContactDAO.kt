package br.com.lucaspires.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.lucaspires.data.source.model.ContactEntity
import io.reactivex.Single

@Dao
interface ContactDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContacts(contactEntity: List<ContactEntity>)

    @Query("SELECT * FROM contactEntity")
    fun getLocalContacts(): Single<List<ContactEntity>>
}
