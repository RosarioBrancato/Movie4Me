package ch.fhnw.movie4me.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.db.MovieListDb;
import ch.fhnw.movie4me.dto.MovieList;

public class MovieListEditActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_LIST_ID = "ch.fhnw.movie4me.ui.MovieListEditActivity.MOVIE_LIST_ID";

    private MovieListDb movieListDb;
    private MovieList movieList;
    private Button buttonSave;
    private EditText editText1;
    private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movielist_edit);

        this.movieListDb = new MovieListDb();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_MOVIE_LIST_ID)) {
            // Get data
            int movieListId = intent.getIntExtra(EXTRA_MOVIE_LIST_ID, -1);
            this.movieList = this.movieListDb.get(movieListId);

            editText1 = findViewById(R.id.nameInput);
            editText2 = findViewById(R.id.descriptionInput);

            actionBar.setTitle("Edit List");
            editText1.setText(this.movieList.getName());
            editText2.setText(this.movieList.getDescription());

            // Toast.makeText(this, "have id", Toast.LENGTH_LONG).show();

        } else {
            actionBar.setTitle("New List");
            this.movieList = new MovieList();

            editText1 = findViewById(R.id.nameInput);
            editText2 = findViewById((R.id.descriptionInput));
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
        //editText1 = findViewById(R.id.nameInput);
        Editable editableName = editText1.getText();
        if (editableName != null) {

            String newListName = editableName.toString();

            if (newListName.length() > 0) {
                //editText2 = findViewById(R.id.descriptionInput);

                String newListDescription = null;
                Editable editableDescr = editText2.getText();
                if (editableDescr != null) {
                    newListDescription = editableDescr.toString();
                }

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

        } else {
            Toast.makeText(this, "Please give the list a name.", Toast.LENGTH_LONG).show();
        }
    }

}
