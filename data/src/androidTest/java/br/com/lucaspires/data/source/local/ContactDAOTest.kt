package br.com.lucaspires.data.source.local

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import br.com.lucaspires.data.source.model.ContactEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContactDAOTest {

    lateinit var db: ContactDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            ContactDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun shouldSuccessToStorage() {
        db.userDao().insertContacts(getUsersListLocal())

        db.userDao()
            .getLocalContacts()
            .test()
            .assertValue { it[1].username == "testUser2" }

    }

    private fun getUsersListLocal() = listOf(
        ContactEntity(0, "", "local test user", "testUser"),
        ContactEntity(1, "", "local test user 2", "testUser2")
    )
}