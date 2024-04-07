package com.moe.moedemo.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moe.moedemo.model.Article
import com.moe.moedemo.repository.NewsApiLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class NewsState(
    val isLoading: Boolean = false,
    val articles: List<Article>? = emptyList(),
    val errorMessage: String? = null
)

sealed class NewsEvent {
    object FetchNews : NewsEvent()
    data class OpenArticle(val url: String) : NewsEvent()
}

class NewsViewModel(val newsApiLoader: NewsApiLoader) : ViewModel() {
    private val _state = mutableStateOf(NewsState())
    val state = _state

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = state.value.copy(isLoading = true)
            // Call loadNewsApi() function to fetch news articles
            val newsResponse = newsApiLoader.loadNewsApi()
            if (newsResponse != null ) {
                _state.value = state.value.copy(isLoading = false, articles = newsResponse.articles)
            } else {
                _state.value = state.value.copy(isLoading = false, errorMessage = "Failed to fetch news articles.")
            }
        }
    }

    fun onEvent(event: NewsEvent) {
        when (event) {
            is NewsEvent.FetchNews -> fetchNews()
            is NewsEvent.OpenArticle -> TODO() // Handle opening article in browser
        }
    }
}
