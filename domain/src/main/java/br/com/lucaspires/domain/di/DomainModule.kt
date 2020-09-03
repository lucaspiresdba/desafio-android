package br.com.lucaspires.domain.di

import br.com.lucaspires.domain.usecase.ContactUseCase
import br.com.lucaspires.domain.usecase.ContactUseCaseImp
import org.koin.dsl.module

val domainModule = module {
    factory<ContactUseCase> { ContactUseCaseImp(get()) }
}