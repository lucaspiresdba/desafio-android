package br.com.lucaspires.domain.usecase

import br.com.lucaspires.data.repository.Repository
import br.com.lucaspires.domain.model.ContactsModel
import br.com.lucaspires.domain.toUserModel
import io.reactivex.Single

class ContactUseCaseImp(
    private val repository: Repository
) :
    ContactUseCase {
    override fun getContacts(): Single<List<ContactsModel>> {
        return repository.getContacts()
            .map { it.toUserModel() }
    }
}