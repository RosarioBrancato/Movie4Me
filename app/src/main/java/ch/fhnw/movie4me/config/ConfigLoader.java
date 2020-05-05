package ch.fhnw.movie4me.config;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ch.fhnw.movie4me.R;

public class ConfigLoader {

    private static ConfigLoader instance;

    private String themoviedbApiKey = null;

    private ConfigLoader() {
    }

    public static ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return instance;
    }

    public void init(Context context) {
        //https://stackoverflow.com/questions/5140539/android-config-file
        Resources resources = context.getResources();

        try {
            InputStream rawResource = resources.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);

            this.themoviedbApiKey = properties.getProperty("themoviedb_apy_key");

        } catch (Resources.NotFoundException e) {
            Log.e(this.getClass().getName(), "Unable to find the config file: " + e.getMessage());
        } catch (IOException e) {
            Log.e(this.getClass().getName(), "Failed to open config file.");
        }
    }

    public String getTheMovieDbApyKey() {
        return this.themoviedbApiKey;
    }

}
