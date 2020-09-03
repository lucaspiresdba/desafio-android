package br.com.lucaspires.domain.usecase

import br.com.lucaspires.domain.model.ContactsModel
import io.reactivex.Single

interface ContactUseCase {
    fun getContacts(): Single<List<ContactsModel>>
}
