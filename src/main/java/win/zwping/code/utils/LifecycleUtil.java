package win.zwping.code.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-04-16 14:01:40 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class LifecycleUtil {

    public static void setLifecycleListener(Lifecycle lifecycle, final OnLifecycleListener listener) {
//        lifecycle.addObserver(new LifecycleEventObserver() {
//            @Override
//            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
//                switch (event) {
//                    case ON_CREATE:
//                        listener.onCreate();
//                        break;
//                    case ON_START:
//                        listener.onStart();
//                        break;
//                    case ON_RESUME:
//                        listener.onResume();
//                        break;
//                    case ON_PAUSE:
//                        listener.onPause();
//                        break;
//                    case ON_STOP:
//                        listener.onStop();
//                        break;
//                    case ON_DESTROY:
//                        listener.onDestroy();
//                        break;
//                }
//            }
//        });
    }

    public  interface OnLifecycleListener {
        void onCreate();

        void onStart();

        void onResume();

        void onPause();

        void onStop();

        void onDestroy();
    }
}
