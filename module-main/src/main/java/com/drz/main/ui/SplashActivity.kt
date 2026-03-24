package com.drz.main.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.drz.main.R
import com.drz.main.theme.AppTheme
import com.tencent.mmkv.MMKV
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                SplashScreen {
                    val isFirstLaunch = MMKV.defaultMMKV().decodeBool("first", true)
                    if (isFirstLaunch) {
                        startActivity(Intent(this, GuideActivity::class.java))
                    } else {
                        // 通过包名+类名解耦，跳转到 app 模块的 AppMainActivity
                        startActivity(
                            Intent().setClassName(packageName, "com.drz.mvvmcomponent.AppMainActivity")
                        )
                    }
                    finish()
                }
            }
        }
    }
}

@Composable
private fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(R.drawable.main_splash_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
