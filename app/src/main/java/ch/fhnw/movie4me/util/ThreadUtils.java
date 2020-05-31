package ch.fhnw.movie4me.util;

import android.util.Log;

public class ThreadUtils {

    public static void runAsync(Runnable target) {
        Thread thread = new Thread(target);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e(ImageUtils.class.getName(), e.getMessage(), e);
        }
    }

}
