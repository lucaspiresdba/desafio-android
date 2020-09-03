package com.picpay.desafio.android.mock

import br.com.lucaspires.data.source.remote.service.PicPayService
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun mockeRetrofit(mockUrl:String) = module {
    single(override = true) {
        get<Retrofit>().create(PicPayService::class.java)
    }

    single(override = true) {
        Retrofit.Builder()
            .baseUrl(mockUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PicPayService::class.java)
    }
}