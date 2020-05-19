package ch.fhnw.movie4me.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageUtils {

    public static Bitmap getBitmapFromUrl(String url) {
        final Bitmap[] bmp = new Bitmap[1];

        Thread thread = new Thread(() -> {
            try {
                InputStream in = new URL(url).openStream();
                bmp[0] = BitmapFactory.decodeStream(in);

            } catch (IOException e) {
                Log.e(ImageUtils.class.getName(), e.getMessage(), e);
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e(ImageUtils.class.getName(), e.getMessage(), e);
        }

        return bmp[0];
    }

}
