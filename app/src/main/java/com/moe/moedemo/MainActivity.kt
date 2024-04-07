package com.moe.moedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.moe.moedemo.repository.NewsApiLoader
import com.moe.moedemo.ui.MainScreen
import com.moe.moedemo.ui.NewsScreen
import com.moe.moedemo.ui.NewsViewModel
import com.moe.moedemo.ui.theme.MoeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoeDemoTheme {
                // A surface container using the 'background' color from the theme
                    val newsViewModel=NewsViewModel(NewsApiLoader())
                    MainScreen(newsViewModel =newsViewModel )

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoeDemoTheme {
        Greeting("Android")
    }
}