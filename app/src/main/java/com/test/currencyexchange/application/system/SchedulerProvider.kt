package com.test.currencyexchange.application.system

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider {

    fun ui(): Scheduler = AndroidSchedulers.mainThread()

    fun io() = Schedulers.io()

    fun computation() = Schedulers.computation()
}