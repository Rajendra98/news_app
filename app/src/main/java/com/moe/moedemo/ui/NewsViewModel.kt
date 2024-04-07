package com.moe.moedemo.ui

import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moe.moedemo.model.Article
import com.moe.moedemo.repository.NewsApiLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class NewsState(
    val isLoading: Boolean = false,
    val articles: MutableList<Article>? = mutableListOf(),
    val errorMessage: String? = null,
    val sOpenUrl:String?=null,

    )

sealed class NewsEvent {
    object FetchNews : NewsEvent()
    object  SortOldToNew:NewsEvent()
    object SortNewToOld:NewsEvent()
    data class ShowDescription(val index:Int):NewsEvent()
}

class NewsViewModel(val newsApiLoader: NewsApiLoader) : ViewModel() {
    private val _state = mutableStateOf(NewsState())
    val state = _state

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(100)
            _state.value = state.value.copy(isLoading = true)
            // Call loadNewsApi() function to fetch news articles
            val newsResponse = newsApiLoader.loadNewsApi()
            if (newsResponse != null ) {
                _state.value = state.value.copy(isLoading = false, articles = newsResponse.articles?.toMutableList())
            } else {
                _state.value = state.value.copy(isLoading = false, errorMessage = "Failed to fetch news articles.")
            }
        }
    }
    // Function to sort the list of articles based on publishedAt time from old to new
    fun sortArticlesOldToNew(articles: List<Article>): List<Article> {
        return articles.sortedBy { article ->
            article.publishedAt
        }
    }

    // Function to sort the list of articles based on publishedAt time from new to old
    fun sortArticlesNewToOld(articles: List<Article>): List<Article> {
        return articles.sortedByDescending { article ->
            article.publishedAt
        }
    }



    fun onEvent(event: NewsEvent) {
        when (event) {
            is NewsEvent.FetchNews -> fetchNews()
            is NewsEvent.SortOldToNew->{
                _state.value=state.value.copy(isLoading = true)
                val list= state.value.articles?.let { sortArticlesOldToNew(it) }
                _state.value=state.value.copy(isLoading = false,articles = list?.toMutableList())

            }
            is NewsEvent.SortNewToOld->{
                _state.value=state.value.copy(isLoading = true)
                val list= state.value.articles?.let { sortArticlesNewToOld(it) }
                _state.value=state.value.copy(isLoading = false,articles = list?.toMutableList())


            }
            is NewsEvent.ShowDescription->{
                //reverse falg
                val updatedArticles = state.value.articles?.mapIndexed { index, article ->
                    if (index == event.index) {
                        // Update the showDes value of the specific article
                        article.copy(showDescription = !article.showDescription)
                    } else {
                        article
                    }
                }

                _state.value=state.value.copy(articles = updatedArticles?.toMutableList())

            }
        }
    }
}
