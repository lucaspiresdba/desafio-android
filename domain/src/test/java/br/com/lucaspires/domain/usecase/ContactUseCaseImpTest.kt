package br.com.lucaspires.domain.usecase

import br.com.lucaspires.data.repository.Repository
import br.com.lucaspires.data.source.local.SharedPreferencesHelper
import br.com.lucaspires.data.source.local.ContactDAO
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

class ContactUseCaseImpTest {

    @Mock
    lateinit var repository: Repository

    lateinit var contactUseCase: ContactUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        contactUseCase = ContactUseCaseImp(repository)
    }

    @Test
    fun shouldBeConvertedToModelList(){
        whenever(repository.getContacts()).thenReturn(getUsersListRemote())

        contactUseCase.getContacts()
            .test()
            .assertValue { it[1].name == "test user 2" }

        verify(repository).getContacts()
    }

    @After
    fun after() {
        Mockito.verifyNoMoreInteractions(repository)
    }

    private fun getUsersListRemote() = Single.just(
        listOf(
            ContactsResponse("", "test user", 0, "testUser"),
            ContactsResponse("", "test user 2", 0, "testUser2")
        )
    )

}