package com.example.healthlog.model

import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    fun isBeforeDate(compareDate: Date) : Observable<String> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        return Observable.just(compareDate)
            .map { dateFormat.format(compareDate) }
            .filter { dateFormat.format(Date()) > it }
    }
}