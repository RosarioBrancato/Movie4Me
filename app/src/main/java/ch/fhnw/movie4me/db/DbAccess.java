package ch.fhnw.movie4me.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ch.fhnw.movie4me.dto.MovieListDetail;

public class DbAccess extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "movie4me.db";
    private static DbAccess instance;


    private DbAccess(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    public static void init(Context context) {
        instance = new DbAccess(context);
    }

    public static DbAccess getInstance() {
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MovieListDb.getCreateTableQuery());
        db.execSQL(MovieListDetailDb.getCreateTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieListDb.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieListDetailDb.TABLE_NAME);
        onCreate(db);
    }
}
