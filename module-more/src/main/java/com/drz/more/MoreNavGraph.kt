package com.drz.more

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.drz.common.navigation.AppRoutes
import com.drz.more.ui.MoreScreen

fun NavGraphBuilder.moreNavGraph(navController: NavController) {
    composable(AppRoutes.MORE) {
        MoreScreen()
    }
}
