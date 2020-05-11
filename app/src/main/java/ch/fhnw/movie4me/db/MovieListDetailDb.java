package ch.fhnw.movie4me.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ch.fhnw.movie4me.dto.MovieList;
import ch.fhnw.movie4me.dto.MovieListDetail;

public class MovieListDetailDb {

    public static final String TABLE_NAME = "MovieListDetail";
    private static final String COL_ID = "Id";
    private static final String COL_MOVIE_LIST_ID = "MovieListId";
    private static final String COL_MOVIE_ID = "MovieId";

    private DbAccess dbAccess;

    public MovieListDetailDb() {
        this.dbAccess = DbAccess.getInstance();
    }

    public static String getCreateTableQuery() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + COL_ID + " INTEGER PRIMARY KEY, "
                + COL_MOVIE_LIST_ID + " INTEGER NOT NULL, "
                + COL_MOVIE_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + COL_MOVIE_LIST_ID + ") REFERENCES " + MovieListDb.TABLE_NAME + "(" + MovieListDb.COL_ID + "))";

        return createTableQuery;
    }

    public MovieListDetail get(long id) {
        MovieListDetail movieListDetail = null;
        String idString = String.valueOf(id);

        SQLiteDatabase db = this.dbAccess.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Id = ?", new String[]{idString});

        if (cursor.moveToFirst()) {
            movieListDetail = new MovieListDetail();
            movieListDetail.setId(cursor.getLong(cursor.getColumnIndex(COL_ID)));
            movieListDetail.setMovieListId(cursor.getLong(cursor.getColumnIndex(COL_MOVIE_LIST_ID)));
            movieListDetail.setMovieId(cursor.getLong(cursor.getColumnIndex(COL_MOVIE_ID)));
        }
        cursor.close();

        return movieListDetail;
    }

    public List<MovieListDetail> getByMovieListId(long movieListId) {
        String movieListIdString = String.valueOf(movieListId);
        List<MovieListDetail> movieListDetails = new ArrayList<>();

        SQLiteDatabase db = this.dbAccess.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_MOVIE_LIST_ID + " = ?", new String[]{movieListIdString});

        while (cursor.moveToNext()) {
            MovieListDetail movieListDetail = new MovieListDetail();
            movieListDetail.setId(cursor.getLong(cursor.getColumnIndex(COL_ID)));
            movieListDetail.setMovieListId(cursor.getLong(cursor.getColumnIndex(COL_MOVIE_LIST_ID)));
            movieListDetail.setMovieId(cursor.getLong(cursor.getColumnIndex(COL_MOVIE_ID)));

            movieListDetails.add(movieListDetail);
        }

        cursor.close();

        return movieListDetails;
    }

    public boolean save(MovieListDetail movieListDetail) {
        boolean success;

        if (movieListDetail.getId() > 0) {
            success = this.update(movieListDetail);
        } else {
            success = this.insert(movieListDetail);
        }

        return success;
    }

    public boolean delete(long id) {
        String idString = String.valueOf(id);

        SQLiteDatabase db = this.dbAccess.getWritableDatabase();
        long rows = db.delete(TABLE_NAME, "Id = ?", new String[]{idString});

        return rows > 0;
    }

    private boolean insert(MovieListDetail movieListDetail) {
        ContentValues values = new ContentValues();
        values.put(COL_MOVIE_LIST_ID, movieListDetail.getMovieListId());
        values.put(COL_MOVIE_ID, movieListDetail.getMovieId());

        SQLiteDatabase db = this.dbAccess.getWritableDatabase();
        long rowId = db.insert(TABLE_NAME, null, values);

        if (rowId > 0) {
            movieListDetail.setId(rowId);
        }

        return rowId > 0;
    }

    private boolean update(MovieListDetail movieListDetail) {
        String idString = String.valueOf(movieListDetail.getId());

        ContentValues values = new ContentValues();
        values.put(COL_MOVIE_LIST_ID, movieListDetail.getMovieListId());
        values.put(COL_MOVIE_ID, movieListDetail.getMovieId());

        SQLiteDatabase db = this.dbAccess.getWritableDatabase();
        long rows = db.update(TABLE_NAME, values, "Id = ?", new String[]{idString});

        return rows > 0;
    }

}
