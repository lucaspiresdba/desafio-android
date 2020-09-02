package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.lucaspires.domain.usecase.UserUseCase
import com.picpay.desafio.android.defaultSchedulers
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    private val composeDisposable = CompositeDisposable()

    private val result = MutableLiveData<ResultState>()
    val isLoading = MutableLiveData<Boolean>()
    val observableResult: LiveData<ResultState> = result

    fun retrieveData() {
        userUseCase.getUsers()
            .defaultSchedulers()
            .doOnSubscribe { isLoading.value = true }
            .doFinally { isLoading.value = false }
            .subscribe(
                {
                    result.value = ResultState.Success(it)
                },
                {
                    result.value = ResultState.OnError
                    it.printStackTrace()
                }).also { composeDisposable.add(it) }
    }

    override fun onCleared() {
        super.onCleared()
        composeDisposable.dispose()
    }
}