package br.com.lucaspires.data.repository

import br.com.lucaspires.data.entityToResponse
import br.com.lucaspires.data.source.local.ContactDAO
import br.com.lucaspires.data.source.local.SharedPreferencesHelper
import br.com.lucaspires.data.source.model.ContactsResponse
import br.com.lucaspires.data.source.remote.service.PicPayService
import br.com.lucaspires.data.toUserEntity
import io.reactivex.Single
import java.io.IOException

class RepositoryImp(
    private val service: PicPayService,
    private val contactDAO: ContactDAO,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : Repository {
    private fun getUsersRemote(): Single<List<ContactsResponse>> {
        return service
            .getUsers()
            .flatMap {
                if (sharedPreferencesHelper.checkIfNeedUpdateCache()) {
                    contactDAO.insertContacts(it.toUserEntity())
                        .run {
                            sharedPreferencesHelper.saveLastUpdate()
                        }
                }
                Single.just(it)
            }
    }

    private fun getUsersLocal(): Single<List<ContactsResponse>> {
        return contactDAO.getLocalContacts()
            .map { it.entityToResponse() }
    }

    override fun getContacts(): Single<List<ContactsResponse>> {
        return getUsersRemote()
            .onErrorResumeNext {
                return@onErrorResumeNext when (it) {
                    is IOException -> getUsersLocal()
                    else -> Single.error(it)
                }
            }
    }
}