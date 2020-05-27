package ch.fhnw.movie4me.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.db.MovieListDb;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.MovieList;
import ch.fhnw.movie4me.ui.movielists.MovieListsFragment;

public class MovieListEditActivity extends AppCompatActivity {

    private MovieListDb movieListDb;
    private MovieList movieList;
    private Button buttonSave;

    public static final String EXTRA_MOVIE_LIST_ID = "ch.fhnw.movie4me.ui.MOVIE_LIST_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_movielist_edit);

        this.movieListDb = new MovieListDb();

        Intent intent = getIntent();
        // Check if Intent is not empty and has data
        if (intent != null && intent.hasExtra(EXTRA_MOVIE_LIST_ID)) {
            // Get data
            int movieListId = intent.getIntExtra(EXTRA_MOVIE_LIST_ID, -1);
            this.movieList = this.movieListDb.get(movieListId);
        } else {
            this.movieList = new MovieList();
        }

        buttonSave = findViewById(R.id.buttonSaveList);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addList();
            }
        });

    }

    public void addList() {
        EditText editText1 = findViewById(R.id.nameInput);
        String newListName = editText1.getText().toString();

        if (newListName != null && newListName.length() > 0) {
            EditText editText2 = findViewById(R.id.descriptionInput);

            String newListDescription = editText2.getText().toString();

            movieList.setName(newListName);
            movieList.setDescription(newListDescription);

            boolean success = this.movieListDb.save(movieList);
            if (success) {
                finish();
            } else {
                Toast.makeText(this.getApplicationContext(), "Saving failed.", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Please give the list a name.", Toast.LENGTH_LONG).show();
        }

    }

}
