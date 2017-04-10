package com.ljheee.mytimewidget;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class TimeService extends Service {

    // 更新 widget 的广播对应的action
    private final String ACTION_UPDATE_ALL = "com.ljheee.widget.UPDATE_TIME";

    // 周期性更新 widget 的周期
    private static final int UPDATE_TIME = 1000;

    // 周期性更新 widget 的线程
    private Context mContext;

    private TimeThread tt;

    public TimeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        mContext = this.getApplicationContext();
        tt = new TimeThread();
        tt.start();

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        // 中断线程，即结束线程。
        if (tt != null) {
            tt.interrupt();
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }


    class TimeThread extends Thread{

        @Override
        public void run() {

            while (true) {
                try {

                    Intent updateIntent = new Intent(ACTION_UPDATE_ALL);
                    mContext.sendBroadcast(updateIntent);

                    Thread.sleep(UPDATE_TIME);

                } catch (InterruptedException e) {
                    // 将 InterruptedException 定义在while循环之外，意味着抛出 InterruptedException 异常时，终止线程。
                    e.printStackTrace();
                }
            }
        }
    }





}
