package win.zwping.code.review.re;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.Timer;

import win.zwping.code.comm.CommCallback;

import java.util.TimerTask;

import static win.zwping.code.utils.HandlerUtil.runOnUiThread;

/*** 绑定生命周期的计时器 ***/
public class TimerSup {

    private Timer timer;
    private TimerTask task;
    private long delay, period;
    private CommCallback<TimerSup> callback;

    public int count; // 执行次数
    public Object extra1,extra2,extra3;

    /*** 只执行一次 ***/
    public TimerSup(CommCallback<TimerSup> callback, long delay) {
        this.callback = callback;
        this.delay = delay;
    }

    public TimerSup(CommCallback<TimerSup> callback, long delay, long period) {
        this.callback = callback;
        this.delay = delay;
        this.period = period;
    }

    /*** 自动管理task的生命周期 ***/
    public TimerSup autoDisposable(@Nullable LifecycleOwner owner, @IntRange(from = 0, to = 2) int lifecycle) {
        if (owner == null) return this;
        owner.getLifecycle().addObserver(new DefaultLifecycleObserver() {

            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                if (lifecycle == 0) cancel();
            }

            @Override
            public void onStop(@NonNull LifecycleOwner owner) {
                if (lifecycle == 1) cancel();
            }

            @Override
            public void onPause(@NonNull LifecycleOwner owner) {
                if (lifecycle == 2) cancel();
            }
        });
        return this;
    }

    /*** 执行任务 ***/
    public TimerSup schedule() {
        cancel();
        count = 0;
        if (timer == null) timer = new Timer();
        if (task == null) task = new TimerTask() {
            @Override
            public void run() {
                ++count;
                taskDetails();
            }
        };
        timer.schedule(task, delay, period);
        return this;
    }

    /*** 执行任务 , 自动管理于生命周期于onDestroy ***/
    public TimerSup schedule(@Nullable LifecycleOwner owner) {
        autoDisposable(owner, 0);
        schedule();
        return this;
    }

    /*** 取消任务 ***/
    public TimerSup cancel() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
        return this;
    }

    private void taskDetails() {
        if (callback != null) {
            runOnUiThread(() -> callback.callback(this));
        }
    }

}
