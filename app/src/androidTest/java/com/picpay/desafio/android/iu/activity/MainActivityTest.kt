package com.picpay.desafio.android.iu.activity

import com.picpay.desafio.android.mock.mockeRetrofit
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules

class MainActivityTest {

    private val server = MockWebServer()

    private fun activityTest(func: RobotMainActivity.() -> Unit) = RobotMainActivity(server)
        .apply { func() }

    @Before
    fun setup() {
        loadKoinModules(mockeRetrofit(server.url("").toString()))
    }

    @Test
    fun shouldBeShowContacsWhenSuccess() {
       activityTest {
           configureResponseSuccess()
           launchActivity()
           matcherContact()
       }
    }

    @Test
    fun shouldBeShowFeedbackWhenError(){
        activityTest {
            configureResponseError()
            launchActivity()
            matcherFeedbackIsVisible()
        }
    }
}