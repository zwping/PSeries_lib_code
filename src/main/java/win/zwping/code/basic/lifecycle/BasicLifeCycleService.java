package win.zwping.code.basic.lifecycle;

import android.app.Service;
import android.content.Intent;

/**
 * <p>describe：service生命周期
 * <p>    note：
 * <p>    note：使用方法
 * <p>    note：
 * <p> @author：zwp on 2019-02-21 16:54:45 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public abstract class BasicLifeCycleService extends Service {

    @Override
    public void onCreate() { // startService下 只会触发一次
        super.onCreate();
    }

//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // startService下 每次开启都会触发
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    // 启动方式
    /*
    startService(Intent)
    stopService(Intent)
     */
    /*
    bindService(Intent, connection, BIND_AUTO_CREATE);
    unbindService(connection);

    ////////activity
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(TAG, "onServiceConnected");
            TestService.MBinder mb = (TestService.MBinder) iBinder;
            mb.playMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TAG, "onServiceDisconnected");
        }
    };

    ////////////Service
    public static class MBinder extends Binder {
        public void playMusic() {
            Log.e("MyBinder", "play music");
        }
    }

     */
}
