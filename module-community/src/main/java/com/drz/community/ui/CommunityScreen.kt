package com.drz.community.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.drz.base.ui.EmptyScreen
import com.drz.base.ui.ErrorScreen
import com.drz.base.ui.LoadingScreen
import com.drz.home.data.model.ItemBean

@Composable
fun CommunityScreen(
    onVideoClick: (Long) -> Unit,
    viewModel: CommunityViewModel = hiltViewModel()
) {
    val tabs = listOf("推荐", "关注")
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(selected = selectedTab == index, onClick = { selectedTab = index },
                    text = { Text(title) })
            }
        }
        val state by if (selectedTab == 0) viewModel.recommendState.collectAsState()
                     else viewModel.attentionState.collectAsState()

        when (val s = state) {
            is CommunityUiState.Loading -> LoadingScreen()
            is CommunityUiState.Error -> ErrorScreen(
                message = s.message,
                onRetry = { if (selectedTab == 0) viewModel.loadRecommend() else viewModel.loadAttention() }
            )
            is CommunityUiState.Success -> {
                if (s.items.isEmpty()) EmptyScreen()
                else VideoFeedList(items = s.items, onVideoClick = onVideoClick)
            }
        }
    }
}

@Composable
private fun VideoFeedList(items: List<ItemBean>, onVideoClick: (Long) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items) { item ->
            val data = when (item.type) {
                "followCard" -> item.data.content?.data ?: item.data
                "videoSmallCard" -> item.data
                else -> null
            } ?: return@items
            Row(
                modifier = Modifier.fillMaxWidth().clickable { onVideoClick(data.id) }
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = data.cover?.detail,
                    contentDescription = data.title,
                    modifier = Modifier.size(120.dp, 80.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(data.title, style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2, overflow = TextOverflow.Ellipsis)
                    Spacer(Modifier.height(4.dp))
                    Text(data.author?.name ?: "", style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}
