package com.drz.main.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.drz.main.R
import com.drz.main.theme.AppTheme
import com.tencent.mmkv.MMKV
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GuideActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                GuideScreen {
                    MMKV.defaultMMKV().encode("first", false)
                    startActivity(
                        Intent().setClassName(packageName, "com.drz.mvvmcomponent.AppMainActivity")
                    )
                    finish()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GuideScreen(onFinish: () -> Unit) {
    val images = listOf(R.drawable.guide0, R.drawable.guide1, R.drawable.guide2)
    val descriptions = listOf(
        "在这里\n你可以发现优质视频内容",
        "在这里\n每天精选好看的视频",
        "在这里\n不再错过可以改变你一生的视频"
    )
    val pagerState = rememberPagerState { images.size }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            Image(
                painter = painterResource(images[page]),
                contentDescription = descriptions[page],
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        val isLast = pagerState.currentPage == images.lastIndex
        Button(
            onClick = {
                if (isLast) onFinish()
                else scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
            },
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 64.dp)
        ) {
            Text(if (isLast) "立即体验" else "下一步")
        }
    }
}
