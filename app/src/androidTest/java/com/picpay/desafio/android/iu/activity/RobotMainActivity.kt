package com.picpay.desafio.android.iu.activity

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.R
import com.picpay.desafio.android.RecyclerViewMatchers
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.io.IOException
import java.io.InputStream

class RobotMainActivity(private val server: MockWebServer) {

    fun configureResponseSuccess() {
        server.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(readFileFromAssets())
        )
    }

    fun configureResponseError() {
        server.enqueue(MockResponse().setResponseCode(404))
    }

    fun matcherContact() {
        RecyclerViewMatchers.checkRecyclerViewItem(
            R.id.recyclerView, 3,
            withText("Fabrício Val")
        )
    }

    fun matcherFeedbackIsVisible() {
        onView(withId(R.id.text_feedback))
            .check(matches(isDisplayed()))
            .check(matches(withText("Ocorreu um erro. Tente novamente.")))
    }

    fun launchActivity() {
        launchActivity<MainActivity>()
    }

    private fun readFileFromAssets(): String {
        return try {
            val inputStream: InputStream =
                InstrumentationRegistry.getInstrumentation().targetContext.assets.open("responseMock.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}