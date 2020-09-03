package br.com.lucaspires.data

import br.com.lucaspires.data.source.model.ContactEntity
import br.com.lucaspires.data.source.model.ContactsResponse

fun List<ContactsResponse>.toUserEntity(): List<ContactEntity> {
    val list = arrayListOf<ContactEntity>()
    this.forEach {
        list.add(ContactEntity(id = it.id, img = it.img, name = it.name, username = it.username))
    }
    return list
}

fun List<ContactEntity>.entityToResponse(): List<ContactsResponse> {
    val list = arrayListOf<ContactsResponse>()
    this.forEach {
        list.add(ContactsResponse(id = it.id, img = it.img, name = it.name, username = it.username))
    }
    return list
}