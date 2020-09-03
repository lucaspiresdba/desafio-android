package br.com.lucaspires.data.repository

import br.com.lucaspires.data.source.local.ContactDAO
import br.com.lucaspires.data.source.local.SharedPreferencesHelper
import br.com.lucaspires.data.source.model.ContactEntity
import br.com.lucaspires.data.source.model.ContactsResponse
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

class RepositoryImpTest {

    @Mock
    lateinit var service: PicPayService

    @Mock
    lateinit var contactDAO: ContactDAO

    @Mock
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    lateinit var repository: Repository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        repository = RepositoryImp(service, contactDAO, sharedPreferencesHelper)
    }

    @After
    fun after() {
        Mockito.verifyNoMoreInteractions(service, contactDAO, sharedPreferencesHelper)
    }

    @Test
    fun shouldBeStorageAndReturnSuccesRemoteCall() {
        whenever(service.getUsers()).thenReturn(getUsersListRemote())
        whenever(sharedPreferencesHelper.checkIfNeedUpdateCache()).thenReturn(true)

        repository.getContacts().test()
            .assertValue { it[1].name == "test user 2" }

        verify(service).getUsers()
        verify(sharedPreferencesHelper).checkIfNeedUpdateCache()
        verify(sharedPreferencesHelper).saveLastUpdate()
        verify(contactDAO).insertContacts(any())
    }

    @Test
    fun shouldNotStorageAndReturnSuccesRemoteCall() {
        whenever(service.getUsers()).thenReturn(getUsersListRemote())
        whenever(sharedPreferencesHelper.checkIfNeedUpdateCache()).thenReturn(false)

        repository.getContacts()
            .test()
            .assertValue { it[0].name == "test user" }

        verify(service).getUsers()
        verify(sharedPreferencesHelper).checkIfNeedUpdateCache()
    }

    @Test
    fun shouldBeIOExceptionAndGetLocalData() {
        whenever(service.getUsers()).thenReturn(Single.error(IOException()))
        whenever(contactDAO.getLocalContacts()).thenReturn(getUsersListLocal())

        repository.getContacts()
            .test()
            .assertValue { it[1].name == "local test user 2" }

        verify(service).getUsers()
        verify(contactDAO).getLocalContacts()
    }

    @Test
    fun shouldBeError() {
        whenever(service.getUsers()).thenReturn(Single.error(Exception("No IOException")))

        repository.getContacts()
            .test()
            .assertError { it.message == "No IOException" }

        verify(service).getUsers()
    }

    private fun getUsersListRemote() = Single.just(
        listOf(
            ContactsResponse("", "test user", 0, "testUser"),
            ContactsResponse("", "test user 2", 0, "testUser2")
        )
    )

    private fun getUsersListLocal() = Single.just(
        listOf(
            ContactEntity(0, "", "local test user", "testUser"),
            ContactEntity(1, "", "local test user 2", "testUser2")
        )
    )
}