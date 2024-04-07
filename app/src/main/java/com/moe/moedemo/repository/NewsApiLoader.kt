package com.moe.moedemo.repository
import com.moe.moedemo.model.Article
import com.moe.moedemo.model.NewsResponse
import com.moe.moedemo.model.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class NewsApiLoader {

    suspend fun loadNewsApi(): NewsResponse? = withContext(Dispatchers.IO) {
        var urlConnection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        var jsonResponse: String? = null

        try {
            val url = URL("https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json")
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.connect()

            val inputStream = urlConnection.inputStream
            val buffer = StringBuffer()
            if (inputStream != null) {
                reader = BufferedReader(InputStreamReader(inputStream))
                var line: String? = reader.readLine()
                while (line != null) {
                    buffer.append(line + "\n")
                    line = reader.readLine()
                }
                jsonResponse = buffer.toString()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            urlConnection?.disconnect()
            try {
                reader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        parseJsonResponse(jsonResponse)
    }

    private fun parseJsonResponse(jsonResponse: String?): NewsResponse? {
        if (jsonResponse.isNullOrEmpty()) return null

        val jsonObject = JSONObject(jsonResponse)
        val status = jsonObject.optString("status")
        val articlesArray = jsonObject.optJSONArray("articles")

        val articlesList = mutableListOf<Article>()
        for (i in 0 until articlesArray.length()) {
            val articleObject = articlesArray.optJSONObject(i)
            val sourceObject = articleObject.optJSONObject("source")
            val source = Source(
                id = sourceObject?.optString("id"),
                name = sourceObject.optString("name")
            )
            var author = articleObject.optString("author")
            if(author=="null")
                author=null
            val title = articleObject.optString("title")
            val description = articleObject.optString("description")
            val url = articleObject.optString("url")
            val urlToImage = articleObject.optString("urlToImage")
            val publishedAt = articleObject.optString("publishedAt")
            val content = articleObject.optString("content")

            val article = Article(source, author, title, description, url, urlToImage, publishedAt, content)
            articlesList.add(article)
        }

        return NewsResponse(status, articlesList)
    }


}