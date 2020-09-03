package br.com.lucaspires.domain.usecase

import br.com.lucaspires.data.source.local.SharedPreferencesHelper
import br.com.lucaspires.data.source.local.ContactDAO
import br.com.lucaspires.data.source.remote.service.PicPayService
import br.com.lucaspires.domain.entityToUserModel
import br.com.lucaspires.domain.model.ContactsModel
import br.com.lucaspires.domain.toUserEntity
import br.com.lucaspires.domain.toUserModel
import io.reactivex.Single
import java.io.IOException

class ContactUseCaseImp(
    private val service: PicPayService,
    private val contactDAO: ContactDAO,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) :
    ContactUseCase {

    private fun getUsersRemote(): Single<List<ContactsModel>> {
        return service
            .getUsers()
            .flatMap {
                if (sharedPreferencesHelper.checkIfNeedUpdateCache()) {
                    contactDAO.insertContacts(it.toUserEntity())
                        .run {
                            sharedPreferencesHelper.saveLastUpdate()
                        }
                }
                Single.just(it.toUserModel())
            }
    }

    private fun getUsersLocal(): Single<List<ContactsModel>> {
        return contactDAO.getLocalContacts()
            .map { it.entityToUserModel() }
    }

    override fun getContacts(): Single<List<ContactsModel>> {
        return getUsersRemote()
            .onErrorResumeNext {
                return@onErrorResumeNext when (it) {
                    is IOException -> getUsersLocal()
                    else -> Single.error(it)
                }
            }
    }
}