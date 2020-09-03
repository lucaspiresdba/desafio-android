package com.picpay.desafio.android.viewmodel

import br.com.lucaspires.domain.model.ContactsModel

sealed class ResultState {
    object OnError : ResultState()
    data class Success(val list: List<ContactsModel>) : ResultState()
    object EmptyList : ResultState()
}