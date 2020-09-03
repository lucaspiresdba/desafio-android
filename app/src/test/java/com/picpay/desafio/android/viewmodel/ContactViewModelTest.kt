package com.picpay.desafio.android.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.lucaspires.domain.model.ContactsModel
import br.com.lucaspires.domain.usecase.ContactUseCase
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ContactViewModelTest {
    @Mock
    lateinit var contactUseCase: ContactUseCase

    @InjectMocks
    lateinit var contactViewModel: ContactViewModel

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun shouldSuccessWhenGetData() {
        whenever(contactUseCase.getContacts()).thenReturn(Single.just(getContactModelList()))

        contactViewModel.retrieveData()

        assertEquals(
            ResultState.Success(getContactModelList()),
            contactViewModel.observableResult.value
        )

        assertEquals(false, contactViewModel.feedbackUser.value)
    }

    @Test
    fun shouldSuccessButEmptyList() {
        whenever(contactUseCase.getContacts()).thenReturn(Single.just(emptyList()))

        contactViewModel.retrieveData()

        assertEquals(ResultState.EmptyList, contactViewModel.observableResult.value)
        assertEquals(true, contactViewModel.feedbackUser.value)
    }

    @Test
    fun shouldErrorWhenGetData() {
        whenever(contactUseCase.getContacts()).thenReturn(Single.error(Exception()))

        contactViewModel.retrieveData()

        assertEquals(ResultState.OnError, contactViewModel.observableResult.value)
        assertEquals(true, contactViewModel.feedbackUser.value)
    }


    private fun getContactModelList() = listOf(
        ContactsModel("", "test", 0, "userNameTest"),
        ContactsModel("", "test1", 0, "userNameTest2")
    )

    @After
    fun after(){
        RxAndroidPlugins.reset()
    }
}