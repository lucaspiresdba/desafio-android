package br.com.lucaspires.domain.di

import br.com.lucaspires.domain.usecase.UserUseCase
import br.com.lucaspires.domain.usecase.UserUseCaseImp
import org.koin.dsl.module

val domainModule = module {
    factory<UserUseCase> { UserUseCaseImp(get(), get(), get()) }
}