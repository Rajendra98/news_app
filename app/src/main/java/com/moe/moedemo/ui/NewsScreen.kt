package com.moe.moedemo.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moe.moedemo.model.Article
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(newsViewModel: NewsViewModel)
{
    Scaffold(

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black,
                    actionIconContentColor = Color.Black

                ),
                navigationIcon = {
                    Icon( Icons.Default.Info, contentDescription =null )
                },
                title = {
                    Text(
                        text = "Moe News",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
            )
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                NewsScreen(newsViewModel)
            }
        }, contentColor = Color.Black,
        containerColor = Color.White,


        bottomBar = {
            BottomAppBar(
                modifier=Modifier.wrapContentHeight(),
                containerColor = Color.White,
                contentColor = Color.Black
            
            ) {
                // Bottom bar content goes here
                ElevatedButton(colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Black
                ), enabled = !newsViewModel.state.value.isLoading, onClick = { newsViewModel.onEvent(NewsEvent.SortOldToNew) }, modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)) {
                    Text(text = "old-to-new ")
                }
                ElevatedButton(colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Black
                ), enabled = !newsViewModel.state.value.isLoading, onClick = { newsViewModel.onEvent(NewsEvent.SortNewToOld) }, modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)) {
                    Text(text = "new-to-old. ")
                }
            }
        }
    )


}
@Composable
fun NewsScreen(newsViewModel: NewsViewModel) {
    val state = newsViewModel.state.value
    val context = LocalContext.current




    if (state.isLoading) {
        // Show loading indicator
        // You can implement a loading Composable here
        LoadingScreen()
    } else if (state.errorMessage != null) {
        // Show error message
        // You can implement an error message Composable here
        ErrorScreen(errorMessage =state.errorMessage)
    } else {
        // Show list of news articles
        state.articles?.let {
            NewsList(articles = it, onItemClick = { article ->
                val urlIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(article.url)
                )
                context.startActivity(urlIntent)
            }, viewModel = newsViewModel)
        }
    }
}





@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(errorMessage: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorMessage,
        )
    }
}


@Composable
fun NewsList(articles: List<Article>, onItemClick: (Article) -> Unit, viewModel: NewsViewModel) {
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        itemsIndexed(articles) { index, article ->
            NewsListItem(index = index, article = article, onItemClick = onItemClick, viewModel)
        }
    }

}


@Composable
fun NewsListItem(index:Int,article: Article, onItemClick: (Article) -> Unit, viewModel: NewsViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        ArticleCard(index,article = article,onItemClick, viewModel = viewModel)
    }
}