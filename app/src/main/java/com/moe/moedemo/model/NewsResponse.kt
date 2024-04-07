package com.moe.moedemo.model

data class NewsResponse(
    val status: String?,
    val articles: List<Article>?
)

data class Article(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    val showDescription:Boolean=false
)

data class Source(
    val id: String?,
    val name: String?
)
