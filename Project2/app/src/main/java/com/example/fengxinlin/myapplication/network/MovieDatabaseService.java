package com.example.fengxinlin.myapplication.network;

import com.example.fengxinlin.myapplication.Object.Movies;
import com.example.fengxinlin.myapplication.Object.Reviews;
import com.example.fengxinlin.myapplication.Object.Trailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by fengxinlin on 11/22/16.
 */
public interface MovieDatabaseService {
    @GET("3/movie/{sort_by}")
    Call<Movies> discoverMovies(@Path("sort_by") String sortBy, @Query("api_key") String apiKey);

    @GET("3/movie/{id}/videos")
    Call<Trailers> findTrailersById(@Path("id") long movieId, @Query("api_key") String apiKey);

    @GET("3/movie/{id}/reviews")
    Call<Reviews> findReviewsById(@Path("id") long movieId, @Query("api_key") String apiKey);
}
