package com.drz.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drz.home.ui.daily.DailyScreen
import com.drz.home.ui.discover.DiscoverScreen
import com.drz.home.ui.nominate.NominateScreen

@Composable
fun HomeScreen(onVideoClick: (Long) -> Unit, onUrlClick: (String) -> Unit) {
    val tabs = listOf("发现", "推荐", "日报")
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        // 顶部导航栏
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左侧日历图标
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "日历",
                    modifier = Modifier.size(22.dp),
                    tint = Color(0xFF333333)
                )
            }
            // 中间 Tab
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                tabs.forEachIndexed { index, title ->
                    val selected = selectedTab == index
                    TabItem(
                        title = title,
                        selected = selected,
                        onClick = { selectedTab = index }
                    )
                }
            }
            // 右侧搜索图标
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "搜索",
                    modifier = Modifier.size(22.dp),
                    tint = Color(0xFF333333)
                )
            }
        }
        HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))

        // 内容区
        when (selectedTab) {
            0 -> DiscoverScreen(onVideoClick = onVideoClick, onUrlClick = onUrlClick)
            1 -> NominateScreen(onVideoClick = onVideoClick)
            2 -> DailyScreen(onVideoClick = { onVideoClick(it.id) })
        }
    }
}

@Composable
private fun TabItem(title: String, selected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = onClick,
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                horizontal = 0.dp, vertical = 4.dp
            )
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = if (selected) Color(0xFF1A1A1A) else Color(0xFF999999)
            )
        }
        // 选中下划线
        if (selected) {
            HorizontalDivider(
                modifier = Modifier
                    .width(16.dp)
                    .padding(top = 0.dp),
                thickness = 2.dp,
                color = Color(0xFF1A1A1A)
            )
        }
    }
}
