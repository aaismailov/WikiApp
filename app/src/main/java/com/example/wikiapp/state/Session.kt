package com.example.wikiapp.state

import android.content.Context
import androidx.core.content.edit
import com.example.wikiapp.data.models.PageShortInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Session(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val _token = MutableStateFlow(sharedPreferences.getString(PREF_TOKEN, "").orEmpty())
    val token: StateFlow<String> = _token

    private val _location = MutableStateFlow(sharedPreferences.getString(PREF_LOCATION, "").orEmpty())
    val location: StateFlow<String> = _location

    private val _pagesStack = MutableStateFlow(
        sharedPreferences.getString(PREF_STACK, "")?.split(SPLITTER)
        ?.filter { it.toIntOrNull() != null }?.map { it.toInt() } ?: listOf())
    val pagesStack: StateFlow<List<Int>> = _pagesStack

    private val _likeStack = MutableStateFlow(
        sharedPreferences.getString(PREF_LIKE, "")?.split(DIVIDER)
            ?.filter { it.split(SPLITTER)[0].toIntOrNull() != null }
            ?.map {
                val pageArray = it.split(SPLITTER)
                PageShortInfo(pageArray[0].toInt(), pageArray[1], pageArray[2], pageArray[3])
            } ?: listOf())
    val likeStack: StateFlow<List<PageShortInfo>> = _likeStack

    fun changeToken(newToken: String) {
        sharedPreferences.edit { putString(PREF_TOKEN, newToken) }
        _token.value = newToken
    }

    fun changeLocation(newLocation: String) {
        sharedPreferences.edit { putString(PREF_LOCATION, newLocation) }
        _location.value = newLocation
    }

    fun addPagesStack(id: Int) {
        val newPagesStack = _pagesStack.value.plus(id)
        sharedPreferences.edit { putString(PREF_STACK, newPagesStack.joinToString(SPLITTER)) }
        _pagesStack.value = newPagesStack
    }

    fun dropLastPagesStack() {
        val newPagesStack = _pagesStack.value.dropLast(1)
        sharedPreferences.edit { putString(PREF_STACK, newPagesStack.joinToString(SPLITTER)) }
        _pagesStack.value = newPagesStack
    }

    fun clearPagesStack() {
        sharedPreferences.edit { putString(PREF_STACK, "") }
        _pagesStack.value = listOf()
    }

    fun getCurrentPageId() = pagesStack.value.last()

    fun likePage(page: PageShortInfo) {
        if (!isPageLiked(page.id) && page.path.isNotEmpty()) {
            val newLikeStack = _likeStack.value.plus(page)
            sharedPreferences.edit {
                putString(PREF_LIKE, newLikeStack.joinToString(DIVIDER) {
                    it.id.toString() + SPLITTER + it.path + SPLITTER + it.title + SPLITTER + it.description
                })
            }
            _likeStack.value = newLikeStack
        }
    }

    fun unlikePage(id: Int) {
        val newLikeStack = _likeStack.value.filter { it.id != id }
        sharedPreferences.edit { putString(PREF_LIKE, newLikeStack.joinToString(DIVIDER) {
            it.id.toString() + SPLITTER + it.path + SPLITTER + it.title + SPLITTER + it.description
        }) }
        _likeStack.value = newLikeStack
    }

    fun isPageLiked(id: Int) = likeStack.value.any { it.id == id }

    companion object {
        const val PREF_TOKEN = "token"
        const val PREF_LOCATION = "location"
        const val PREF_NAME = "session"
        const val PREF_STACK = "pages_stack"
        const val PREF_LIKE = "like_stack"
        const val DIVIDER = "_|-|_"
        const val SPLITTER = ";"
    }
}