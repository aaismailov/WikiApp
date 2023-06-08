package com.example.wikiapp.utils

import android.util.Log
import androidx.annotation.StringRes
import com.example.wikiapp.PagesListQuery
import com.example.wikiapp.R
import kotlinx.coroutines.flow.MutableStateFlow

sealed class Result<T>(
    var data: T? = null,
    @StringRes val message: Int? = null
)

class SuccessResult<T>(data: T?) : Result<T>(data = data)
class LoadingResult<T>(data: T? = null) : Result<T>(data = data)
class ErrorResult<T>(
    @StringRes message: Int? = null
) : Result<T>(message = message)
class NothingResult<T> : Result<T>()

inline fun <T> MutableResultFlow<T>.loadOrError(
    @StringRes message: Int = R.string.on_error_def,
    isLoadingResult: Boolean = true,
    load: () -> T?
) {
    if (isLoadingResult)
        value = LoadingResult()

    value = try {
        SuccessResult(load())
    } catch (e: Exception) {
        ErrorResult(message)
    }
}

fun <T> MutableResultFlow(value: Result<T> = NothingResult()) = MutableStateFlow(value)
typealias MutableResultFlow<T> = MutableStateFlow<Result<T>>

fun getIdFromUrl(url: String, pages: List<PagesListQuery.List>): Int {
    var path = url.split('#')[0]
    if (path.contains("wiki.miem.hse.ru"))
        path = path.split("wiki.miem.hse.ru")[1]
    if (path.isNotEmpty()){
        if (path.startsWith("/ru/"))
            path = path.drop(4)
        if (path.first() == '/')
            path = path.drop(1)
        if (path.last() == '/')
            path = path.dropLast(1)
    } else path = "home"
    Log.d("PATH++++++++++", path)

    var id = 0
    run loop@{
        pages.forEach { item ->
            if (item.path == path) {
                id = item.id
                return@loop
            }
        }
    }
    Log.d("ID++++++++++", "$id")
    return id
}
