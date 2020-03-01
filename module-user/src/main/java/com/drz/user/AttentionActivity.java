package com.drz.user;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.drz.common.router.RouterActivityPath;

/**
 * 应用模块:
 * <p>
 * 类描述: 关注页面
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-29
 */
@Route(path = RouterActivityPath.User.PAGER_ATTENTION)
public class AttentionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_attention);
    }
}
