package br.com.lucaspires.domain.usecase

import br.com.lucaspires.data.source.local.SharedPreferencesHelper
import br.com.lucaspires.data.source.local.UserDAO
import br.com.lucaspires.data.source.model.UserEntity
import br.com.lucaspires.data.source.model.UserResponse
import br.com.lucaspires.data.source.remote.service.PicPayService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.IOException

class UserUseCaseImpTest {

    @Mock
    lateinit var service: PicPayService

    @Mock
    lateinit var userDAO: UserDAO

    @Mock
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    lateinit var userUseCase: UserUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        userUseCase = UserUseCaseImp(service, userDAO, sharedPreferencesHelper)
    }

    @After
    fun after() {
        Mockito.verifyNoMoreInteractions(service, userDAO, sharedPreferencesHelper)
    }

    @Test
    fun shouldBeStorageAndReturnSuccesRemoteCall() {
        whenever(service.getUsers()).thenReturn(getUsersListRemote())
        whenever(sharedPreferencesHelper.checkIfNeedUpdateCache()).thenReturn(true)

        userUseCase.getUsers().test()
            .assertValue { it[1].name == "test user 2" }

        verify(service).getUsers()
        verify(sharedPreferencesHelper).checkIfNeedUpdateCache()
        verify(sharedPreferencesHelper).saveLastUpdate()
        verify(userDAO).insertUsers(any())
    }

    @Test
    fun shouldNotStorageAndReturnSuccesRemoteCall() {
        whenever(service.getUsers()).thenReturn(getUsersListRemote())
        whenever(sharedPreferencesHelper.checkIfNeedUpdateCache()).thenReturn(false)

        userUseCase.getUsers()
            .test()
            .assertValue { it[0].name == "test user" }

        verify(service).getUsers()
        verify(sharedPreferencesHelper).checkIfNeedUpdateCache()
    }

    @Test
    fun shouldBeIOExceptionAndGetLocalData() {
        whenever(service.getUsers()).thenReturn(Single.error(IOException()))
        whenever(userDAO.getUsersLocal()).thenReturn(getUsersListLocal())

        userUseCase.getUsers()
            .test()
            .assertValue { it[1].name == "local test user 2" }

        verify(service).getUsers()
        verify(userDAO).getUsersLocal()
    }

    @Test
    fun shouldBeError() {
        whenever(service.getUsers()).thenReturn(Single.error(Exception("No IOException")))

        userUseCase.getUsers()
            .test()
            .assertError { it.message == "No IOException" }

        verify(service).getUsers()
    }

    private fun getUsersListRemote() = Single.just(
        listOf(
            UserResponse("", "test user", 0, "testUser"),
            UserResponse("", "test user 2", 0, "testUser2")
        )
    )

    private fun getUsersListLocal() = Single.just(
        listOf(
            UserEntity(0, "", "local test user", "testUser"),
            UserEntity(1, "", "local test user 2", "testUser2")
        )
    )
}