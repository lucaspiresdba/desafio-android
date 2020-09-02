package com.picpay.desafio.android

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

fun <T> Single<T>.defaultSchedulers() = this.observeOn(AndroidSchedulers.mainThread())

fun View.isToHide(boolean: Boolean) {
    if (boolean) this.visibility = VISIBLE else this.visibility = GONE
}