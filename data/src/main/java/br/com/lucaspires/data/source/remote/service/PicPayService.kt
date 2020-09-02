package br.com.lucaspires.data.source.remote.service

import br.com.lucaspires.data.source.model.UserResponse
import io.reactivex.Single
import retrofit2.http.GET

interface PicPayService {

    @GET("users")
    fun getUsers(): Single<List<UserResponse>>
}