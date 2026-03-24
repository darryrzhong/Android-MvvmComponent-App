package com.drz.mvvmcomponent.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.drz.common.navigation.AppRoutes
import com.drz.community.communityNavGraph
import com.drz.home.homeNavGraph
import com.drz.more.moreNavGraph
import com.drz.mvvmcomponent.ui.WebViewScreen
import com.drz.player.playerNavGraph
import java.net.URLDecoder

data class BottomNavItem(
    val route: String,
    val label: String,
    val iconResId: Int
)

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val bottomNavItems = listOf(
        BottomNavItem(AppRoutes.HOME, "首页", com.drz.main.R.drawable.main_home),
        BottomNavItem(AppRoutes.COMMUNITY, "社区", com.drz.main.R.drawable.main_community),
        BottomNavItem(AppRoutes.MORE, "通知", com.drz.main.R.drawable.main_notify),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = bottomNavItems.any { it.route == currentDestination?.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(painterResource(item.iconResId), contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppRoutes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            homeNavGraph(navController)
            communityNavGraph(navController)
            moreNavGraph(navController)
            playerNavGraph(navController)
            composable(AppRoutes.WEB_VIEW) { backStackEntry ->
                val encodedUrl = backStackEntry.arguments?.getString("url") ?: ""
                val url = URLDecoder.decode(encodedUrl, "UTF-8")
                WebViewScreen(url = url, onBack = { navController.popBackStack() })
            }
        }
    }
}
