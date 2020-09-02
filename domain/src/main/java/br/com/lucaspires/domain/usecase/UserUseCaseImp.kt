package br.com.lucaspires.domain.usecase

import br.com.lucaspires.data.source.local.SharedPreferencesHelper
import br.com.lucaspires.data.source.local.UserDAO
import br.com.lucaspires.data.source.remote.service.PicPayService
import br.com.lucaspires.domain.entityToUserModel
import br.com.lucaspires.domain.model.UserModel
import br.com.lucaspires.domain.toUserEntity
import br.com.lucaspires.domain.toUserModel
import io.reactivex.Single
import java.io.IOException

class UserUseCaseImp(
    private val service: PicPayService,
    private val userDAO: UserDAO,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) :
    UserUseCase {

    private fun getUsersRemote(): Single<List<UserModel>> {
        return service
            .getUsers()
            .flatMap {
                if (sharedPreferencesHelper.checkIfNeedUpdateCache()) {
                    userDAO.insertUsers(it.toUserEntity())
                        .run {
                            sharedPreferencesHelper.saveLastUpdate()
                        }
                }
                Single.just(it.toUserModel())
            }
    }

    private fun getUsersLocal(): Single<List<UserModel>> {
        return userDAO.getUsersLocal()
            .map { it.entityToUserModel() }
    }

    override fun getUsers(): Single<List<UserModel>> {
        return getUsersRemote()
            .onErrorResumeNext {
                return@onErrorResumeNext when (it) {
                    is IOException -> getUsersLocal()
                    else -> Single.error(it)
                }
            }
    }
}