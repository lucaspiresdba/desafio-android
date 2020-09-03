package br.com.lucaspires.data.source.local

import android.content.Context
import android.content.SharedPreferences
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SharedPreferencesHelperImpTest {
    lateinit var sharedPreferencesHelperImp: SharedPreferencesHelper
    lateinit var sharedPref: SharedPreferences

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        sharedPref = context.getSharedPreferences(
            context.packageName,
            Context.MODE_PRIVATE
        )
        sharedPreferencesHelperImp = SharedPreferencesHelperImp(sharedPref)
    }

    @Test
    fun shoudlBeSuccessSaveLastUpdate() {
        sharedPref.edit().clear()

        val currentTime = System.currentTimeMillis()

        sharedPreferencesHelperImp.saveLastUpdate()

        assertEquals(currentTime, sharedPref.getLong("lastUpdateCache", 0))
    }

    @Test
    fun shouldNeedUpdate() {
        sharedPref.edit().clear()

        val currentTime = System.currentTimeMillis()

        sharedPref.edit().putLong("lastUpdateCache", (currentTime - (16 * 60000))).commit()

        assertEquals(sharedPreferencesHelperImp.checkIfNeedUpdateCache(), true)
    }

    @Test
    fun shouldNotNeedUpdate() {
        sharedPref.edit().clear()

        val currentTime = System.currentTimeMillis()

        sharedPref.edit().putLong("lastUpdateCache", (currentTime - (13 * 60000))).commit()

        assertEquals(sharedPreferencesHelperImp.checkIfNeedUpdateCache(), false)
    }
}