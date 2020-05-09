package ch.fhnw.movie4me.ui.movielists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieListsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MovieListsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}