package com.ming.slove.base.app;

import android.os.Environment;

/**
 * 用于管理所有全局变量
 * Created by Ming on 2016/11/30.
 */

public class APPS {
    //API地址接口
    public static final String BASE_URL = "";//API接口的主机地址

    //存储目录路径
    public static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/MingAppk/";
    public static final String FILE_PATH_HTTPCACHE = Environment.getExternalStorageDirectory() + "/MingAppk/HttpCache/";
    public static final String FILE_PATH_GLIDECACHE = Environment.getExternalStorageDirectory() + "/MingAppk/GlideCache/";
}
