package win.zwping.code.basic.lifecycle;

import android.app.Application;
import android.content.res.Configuration;

/**
 * <p>describe：Application生命周期
 * <p>    note：
 * <p> @author：zwp on 2019-02-21 16:30:00 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class BasicLifeCycleApp extends Application {

    @Override
    public void onCreate() {  // 程序创建的时候执行
        super.onCreate();
    }

    @Override
    public void onTerminate() { // 程序终止的时候执行
        super.onTerminate();
    }

    @Override
    public void onLowMemory() { // 低内存的时候执行
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) { // 程序在内存清理的时候执行（回收内存）
        super.onTrimMemory(level);        // HOME键退出应用程序、长按MENU键，打开Recent TASK都会执行
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
