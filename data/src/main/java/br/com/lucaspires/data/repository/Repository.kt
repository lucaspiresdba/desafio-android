package br.com.lucaspires.data.repository

import br.com.lucaspires.data.source.model.ContactsResponse
import io.reactivex.Single

interface Repository {
    fun getContacts(): Single<List<ContactsResponse>>
}