package com.moe.moedemo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.moe.moedemo.R
import com.moe.moedemo.model.Article

@Composable
fun ArticleCard(index:Int,article: Article, onItemClick: (Article) -> Unit,viewModel: NewsViewModel) {


    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(7.dp),colors=CardDefaults.cardColors(
            containerColor= Color.White,
        contentColor= Color.Black,
            disabledContainerColor=Color.White,
        disabledContentColor=Color.Gray)        ,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp)

    ){
        Row(verticalAlignment = Alignment.CenterVertically) {
            article.urlToImage?.let { imageUrl ->
                Image(
                    contentScale = ContentScale.FillHeight,

                    painter = rememberImagePainter(data = imageUrl,
                            builder = {
                        crossfade(true)
                        placeholder(R.drawable.news) // Placeholder image resource
                    }
                    ), contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(8.dp)
                        .border(width = 1.dp, color = Color.Gray, shape = CircleShape)
                        .clip(
                            shape = CircleShape
                        )

                )

            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = article.title?:"Unknown",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onItemClick(article)
                        }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.author?:article.source?.name?:"Unknown",
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.publishedAt?:"Unknown",
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
               
            }
        }
        if(article.showDescription)
        {
            Text(
                text = article.description?:"No Description present",
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = { viewModel.onEvent(NewsEvent.ShowDescription(index = index)) },
            modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(end = 4.dp),
                    color = Color.Black,
                    textAlign = TextAlign.End,
                    text = if (article.showDescription) "Show less" else "Show more"
                )
                Icon(
                    if (article.showDescription) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }





    }
}
