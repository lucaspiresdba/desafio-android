package br.com.lucaspires.domain.usecase

import br.com.lucaspires.domain.model.UserModel
import io.reactivex.Single

interface UserUseCase {
    fun getUsers(): Single<List<UserModel>>
}
