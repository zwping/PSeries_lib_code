package win.zwping.code.basic;


import android.content.Context;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.*;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import win.zwping.code.Util;
import win.zwping.code.basic.lifecycle.BasicLifeCycleApp;
import win.zwping.code.basic.pi.IApp;
import win.zwping.code.utils.AppUtil;
import win.zwping.code.utils.LogUtil;

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
        LogUtil.getConfig().setGlobalTag("TAG");

        init();
        if (AppUtil.isAppDebug()) debugInit();
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(android.R.color.black, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
}
