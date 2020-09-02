package br.com.lucaspires.data.source.remote.service.intercepter

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request()).newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control")
            .header(
                "Cache-Control", CacheControl.Builder()
                    .maxAge(20, TimeUnit.MINUTES)
                    .build().toString()
            )
            .build()
    }
}