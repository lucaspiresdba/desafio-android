package com.picpay.desafio.android

class ExampleService(
    private val service: br.com.lucaspires.data.source.remote.PicPayService
) {

    fun example(): List<br.com.lucaspires.data.source.model.User> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}