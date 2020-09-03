package br.com.lucaspires.data.source.local

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import br.com.lucaspires.data.source.model.UserEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDAOTest {

    lateinit var db: UserDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            UserDatabase::class.java
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
        db.userDao().insertUsers(getUsersListLocal())

        db.userDao()
            .getUsersLocal()
            .test()
            .assertValue { it[1].username == "testUser2" }

    }

    private fun getUsersListLocal() = listOf(
        UserEntity(0, "", "local test user", "testUser"),
        UserEntity(1, "", "local test user 2", "testUser2")
    )
}