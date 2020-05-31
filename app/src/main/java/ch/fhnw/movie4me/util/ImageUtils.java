package ch.fhnw.movie4me.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageUtils {

    public static Bitmap getBitmapFromUrl(String url) {
        Bitmap bitmap = null;

        if (url != null && url.length() > 0) {
            final Bitmap[] bmp = new Bitmap[1];

            ThreadUtils.runAsync(() -> {
                InputStream in = null;

                try {
                    in = new URL(url).openStream();
                } catch (IOException e) {
                    Log.e(ImageUtils.class.getName(), e.getMessage(), e);
                }

                if (in != null) {
                    bmp[0] = BitmapFactory.decodeStream(in);
                }
            });

            bitmap = bmp[0];
        }

        return bitmap;
    }

}
