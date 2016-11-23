package com.example.fengxinlin.myapplication.details;

import android.os.AsyncTask;
import android.util.Log;

import com.example.fengxinlin.myapplication.BuildConfig;
import com.example.fengxinlin.myapplication.Object.Review;
import com.example.fengxinlin.myapplication.Object.Reviews;
import com.example.fengxinlin.myapplication.network.MovieDatabaseService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fengxinlin on 11/22/16.
 */
public class FetchReviewsTask extends AsyncTask<Long, Void, List<Review>> {
    public static String LOG_TAG = FetchReviewsTask.class.getSimpleName();
    private final Listener mListener;


    interface Listener {
        void onReviewsFetchFinished(List<Review> reviews);
    }

    public FetchReviewsTask(Listener listener) {
        mListener = listener;
    }

    @Override
    protected List<Review> doInBackground(Long... params) {
        if (params.length == 0) {
            return null;
        }
        long movieId = params[0];

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieDatabaseService service = retrofit.create(MovieDatabaseService.class);
        Call<Reviews> call = service.findReviewsById(movieId,
                BuildConfig.THE_MOVIE_DATABASE_API_KEY);
        try {
            Response<Reviews> response = call.execute();
            Reviews reviews = response.body();
            return reviews.getReviews();
        } catch (IOException e) {
            Log.e(LOG_TAG, "A problem occurred talking to the movie db ", e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Review> reviews) {
        if (reviews != null) {
            mListener.onReviewsFetchFinished(reviews);
        } else {
            mListener.onReviewsFetchFinished(new ArrayList<Review>());
        }
    }
}
