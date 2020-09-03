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

fun List<ContactsResponse>.toUserEntity(): List<ContactEntity> {
    val list = arrayListOf<ContactEntity>()
    this.forEach {
        list.add(ContactEntity(id = it.id, img = it.img, name = it.name, username = it.username))
    }
    return list
}

fun List<ContactEntity>.entityToUserModel(): List<ContactsModel> {
    val list = arrayListOf<ContactsModel>()
    this.forEach {
        list.add(ContactsModel(id = it.id, img = it.img, name = it.name, username = it.username))
    }
    return list
}