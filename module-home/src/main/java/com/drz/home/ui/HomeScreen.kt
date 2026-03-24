package com.drz.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.drz.home.ui.discover.DiscoverScreen
import com.drz.home.ui.nominate.NominateScreen

@Composable
fun HomeScreen(onVideoClick: (Long) -> Unit, onUrlClick: (String) -> Unit) {
    val tabs = listOf("发现", "推荐")
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        when (selectedTab) {
            0 -> DiscoverScreen(onVideoClick = onVideoClick, onUrlClick = onUrlClick)
            1 -> NominateScreen(onVideoClick = onVideoClick)
        }
    }
}
