package com.picpay.desafio.android.viewmodel

import br.com.lucaspires.domain.model.UserModel

sealed class ResultState {
    object OnError : ResultState()
    data class Success(val list: List<UserModel>) : ResultState()
}