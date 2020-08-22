package ch.fhnw.movie4me.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ch.fhnw.movie4me.dto.MovieList;

public class MovieListDb {

    public static final String TABLE_NAME = "MovieList";
    public static final String COL_ID = "Id";
    private static final String COL_NAME = "Name";
    private static final String COL_DESCRIPTION = "Description";

    private DbAccess dbAccess;


    public MovieListDb() {
        this.dbAccess = DbAccess.getInstance();
    }


    public static String getCreateTableQuery() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + COL_ID + " INTEGER PRIMARY KEY, "
                + COL_NAME + " TEXT NOT NULL, "
                + COL_DESCRIPTION + " TEXT)";

        return createTableQuery;
    }


    public MovieList get(long id) {
        MovieList movieList = null;
        String idString = String.valueOf(id);

        SQLiteDatabase db = this.dbAccess.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Id = ?", new String[]{idString});

        if (cursor.moveToFirst()) {
            movieList = new MovieList();
            movieList.setId(cursor.getLong(cursor.getColumnIndex(COL_ID)));
            movieList.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
            movieList.setDescription(cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION)));
        }
        cursor.close();

        return movieList;
    }

    public List<MovieList> getAll() {
        List<MovieList> movieLists = new ArrayList<>();

        SQLiteDatabase db = this.dbAccess.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        while (cursor.moveToNext()) {
            MovieList movieList = new MovieList();
            movieList.setId(cursor.getLong(cursor.getColumnIndex(COL_ID)));
            movieList.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
            movieList.setDescription(cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION)));

            movieLists.add(movieList);
        }

        cursor.close();

        return movieLists;
    }

    public boolean save(MovieList movieList) {
        boolean success;

        if (movieList.getId() > 0) {
            success = this.update(movieList);
        } else {
            success = this.insert(movieList);
        }

        return success;
    }

    public boolean delete(long id) {
        String idString = String.valueOf(id);

        MovieListDetailDb detailDb = new MovieListDetailDb();
        detailDb.deleteByMovieListId(id);

        SQLiteDatabase db = this.dbAccess.getWritableDatabase();
        long rows = db.delete(TABLE_NAME, "Id = ?", new String[]{idString});

        return rows > 0;
    }

    private boolean insert(MovieList movieList) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, movieList.getName());
        values.put(COL_DESCRIPTION, movieList.getDescription());

        SQLiteDatabase db = this.dbAccess.getWritableDatabase();
        long rowId = db.insert(TABLE_NAME, null, values);

        if (rowId > 0) {
            movieList.setId(rowId);
        }

        return rowId > 0;
    }

    private boolean update(MovieList movieList) {
        String idString = String.valueOf(movieList.getId());

        ContentValues values = new ContentValues();
        values.put(COL_NAME, movieList.getName());
        values.put(COL_DESCRIPTION, movieList.getDescription());

        SQLiteDatabase db = this.dbAccess.getWritableDatabase();
        long rows = db.update(TABLE_NAME, values, "Id = ?", new String[]{idString});

        return rows > 0;
    }

}
