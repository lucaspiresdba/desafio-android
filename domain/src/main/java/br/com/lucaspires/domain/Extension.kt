package br.com.lucaspires.domain

import br.com.lucaspires.data.source.model.ContactEntity
import br.com.lucaspires.data.source.model.ContactsResponse
import br.com.lucaspires.domain.model.ContactsModel

fun List<ContactsResponse>.toUserModel(): List<ContactsModel> {
    val list = arrayListOf<ContactsModel>()
    this.forEach {
        list.add(ContactsModel(img = it.img, name = it.name, id = it.id, username = it.username))
    }
    return list
}