package win.zwping.code.basic;

import com.coder.zzq.smartshow.core.SmartShow;

import win.zwping.code.Util;
import win.zwping.code.basic.lifecycle.BasicLifeCycleApp;
import win.zwping.code.basic.pi.IApp;
import win.zwping.code.utils.AppUtil;

/**
 * <p>describe：App基类，初始化code框架中必备框架
 * <p>    note：
 * <p> @author：zwp on 2019-02-21 16:56:40 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public abstract class BasicApp extends BasicLifeCycleApp implements IApp {

    @Override
    public void onCreate() {
        super.onCreate();

        Util.init(getApplicationContext());
//        Hawk.init(this).build();
        SmartShow.init(this);

        init();
        if (AppUtil.isAppDebug()) debugInit();
    }
}
