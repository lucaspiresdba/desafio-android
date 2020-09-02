package br.com.lucaspires.domain

import br.com.lucaspires.data.source.model.UserEntity
import br.com.lucaspires.data.source.model.UserResponse
import br.com.lucaspires.domain.model.UserModel

fun List<UserResponse>.toUserModel(): List<UserModel> {
    val list = arrayListOf<UserModel>()
    this.forEach {
        list.add(UserModel(img = it.img, name = it.name, id = it.id, username = it.username))
    }
    return list
}

fun List<UserResponse>.toUserEntity(): List<UserEntity> {
    val list = arrayListOf<UserEntity>()
    this.forEach {
        list.add(UserEntity(id = it.id, img = it.img, name = it.name, username = it.username))
    }
    return list
}

fun List<UserEntity>.entityToUserModel(): List<UserModel> {
    val list = arrayListOf<UserModel>()
    this.forEach {
        list.add(UserModel(id = it.id, img = it.img, name = it.name, username = it.username))
    }
    return list
}