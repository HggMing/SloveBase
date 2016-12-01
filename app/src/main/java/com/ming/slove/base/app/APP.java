package com.ming.slove.base.app;

import android.app.Application;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.orhanobut.hawk.Hawk;

public class APP extends Application {
    /**
     * 单例模式中获取唯一的Application实例
     */
    private static APP instance;

    public static APP getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //主题切换初始化
        ThemeUtils.setSwitchColor(ThemeHelper.getSwitchColor());
        //用于存储
        Hawk.init(this).build();
    }
}
