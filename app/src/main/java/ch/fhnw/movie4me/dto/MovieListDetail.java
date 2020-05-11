package ch.fhnw.movie4me.dto;

public class MovieListDetail {

    private long id;
    private long movieListId;
    private long movieId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMovieListId() {
        return movieListId;
    }

    public void setMovieListId(long movieListId) {
        this.movieListId = movieListId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }
}
