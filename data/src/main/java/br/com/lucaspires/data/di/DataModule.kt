package br.com.lucaspires.data.di

import android.content.Context
import androidx.room.Room
import br.com.lucaspires.data.repository.Repository
import br.com.lucaspires.data.repository.RepositoryImp
import br.com.lucaspires.data.source.local.ContactDatabase
import br.com.lucaspires.data.source.local.SharedPreferencesHelper
import br.com.lucaspires.data.source.local.SharedPreferencesHelperImp
import br.com.lucaspires.data.source.remote.service.PicPayService
import br.com.lucaspires.data.source.remote.service.intercepter.CacheInterceptor
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

private const val BASE_URL = "http://careers.picpay.com/tests/mobdev/"
private const val CACHE_SIZE = (10 * 1024 * 1024).toLong()
private const val CACHE_DIR_NAME = "cache-pp"

val dataModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PicPayService::class.java)
    }

    single {
        OkHttpClient.Builder()
            .apply {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                addInterceptor(CacheInterceptor())
                cache(get())
            }.build()
    }

    single {
        Cache(File(androidContext().cacheDir, CACHE_DIR_NAME), CACHE_SIZE)
    }

    single {
        Room.databaseBuilder(get(), ContactDatabase::class.java, "picpay-db")
            .allowMainThreadQueries()
            .build()
            .userDao()
    }

    single {
        androidContext().getSharedPreferences(androidContext().packageName, Context.MODE_PRIVATE)
    }

    single<SharedPreferencesHelper> {
        SharedPreferencesHelperImp(get())
    }

    factory<Repository> { RepositoryImp(get(), get(), get()) }
}
