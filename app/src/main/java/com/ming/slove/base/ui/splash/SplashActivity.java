package com.ming.slove.base.ui.splash;

import android.os.Bundle;

import com.ming.slove.base.R;
import com.ming.slove.base.common.base.BaseActivity;
import com.ming.slove.base.common.utils.ActivityUtils;
import com.ming.slove.base.common.utils.BaseTools;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseTools.setFullScreen(this);//隐藏状态栏
        setContentView(R.layout.activity_splash);

        SplashFragment splashFragment =
                (SplashFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (splashFragment == null) {
            splashFragment = SplashFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), splashFragment, R.id.contentFrame);
        }

        new SplashPresenter(splashFragment);
    }
}
