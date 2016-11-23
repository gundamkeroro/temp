package com.example.fengxinlin.myapplication.network;

import android.os.AsyncTask;
import android.support.annotation.StringDef;
import android.util.Log;

import com.example.fengxinlin.myapplication.BuildConfig;
import com.example.fengxinlin.myapplication.Object.Movie;
import com.example.fengxinlin.myapplication.Object.Movies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fengxinlin on 11/16/16.
 */
public class FetchMovieTask extends AsyncTask<String, Void, List<Movie>> {
    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    public final static String MOST_POPULAR = "popular";
    public final static String TOP_RATED = "top_rated";
    public final static String FAVORITES = "favorites";

    @StringDef({MOST_POPULAR, TOP_RATED, FAVORITES})
    public @interface SORT_BY {
    }

    private final NotifyAboutTaskCompletionCommand mCommand;
    private
    @SORT_BY
    String mSortBy = MOST_POPULAR;

    public interface Listener {
        void onFetchFinished(Command command);
    }

    public static class NotifyAboutTaskCompletionCommand implements Command {
        private FetchMovieTask.Listener mListener;
        private List<Movie> mMovies;

        public NotifyAboutTaskCompletionCommand(FetchMovieTask.Listener listener) {
            mListener = listener;
        }

        @Override
        public void execute() {
            mListener.onFetchFinished(this);
        }

        public List<Movie> getMovies() {
            return mMovies;
        }
    }

    public FetchMovieTask(@SORT_BY String sortBy, NotifyAboutTaskCompletionCommand command) {
        mCommand = command;
        mSortBy = sortBy;
    }


    @Override
    protected List<Movie> doInBackground(String... params) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieDatabaseService service = retrofit.create(MovieDatabaseService.class);
        Call<Movies> call = service.discoverMovies(mSortBy,
                BuildConfig.THE_MOVIE_DATABASE_API_KEY);
        try {
            Response<Movies> response = call.execute();
            Movies movies = response.body();
            return movies.getMovies();

        } catch (IOException e) {
            Log.e(LOG_TAG, "A problem occurred talking to the movie db ", e);
        }
        return null;
    }
    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies != null) {
            mCommand.mMovies = movies;
        } else {
            mCommand.mMovies = new ArrayList<>();
        }
        mCommand.execute();
    }
}