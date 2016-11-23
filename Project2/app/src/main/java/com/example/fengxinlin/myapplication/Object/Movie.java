package com.example.fengxinlin.myapplication.Object;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fengxinlin on 11/15/16.
 */
public class Movie implements Parcelable {
    public static final String LOG_TAG = Movie.class.getSimpleName();
    public static final float POSTER_ASPECT_RATIO = 1.5f;
    private static final String DOWNLOADING_POSTER = "http://image.tmdb.org/t/p/w342";
    private static final String DOWNLOADING_BACKDROP = "http://image.tmdb.org/t/p/original";
    private static final String RELEASE_DATE_UNKNOW = "Release date is unknown";

    @SerializedName("id")
    private long id;
    @SerializedName("original_title")
    private String title;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_average")
    private String voteAverage;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("backdrop_path")
    private String backdrop;

    private Movie() {
    }

    public Movie(long id, String title, String poster, String overview,
                 String voteAverage, String releaseDate, String backdrop){
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.backdrop = backdrop;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverview() {
        return overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getReleaseDate(Context context) {
        String inputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.US);
        if (releaseDate != null && !releaseDate.isEmpty()) {
            try {
                Date date = inputFormat.parse(releaseDate);
                return DateFormat.getDateInstance().format(date);
            } catch (ParseException e) {
                Log.e(LOG_TAG, "The Release data was not parsed successfully: " + releaseDate);
            }
        } else {
            releaseDate = RELEASE_DATE_UNKNOW;
        }
        return releaseDate;
    }

    public String getBackdrop() {
        return backdrop;
    }

    @Nullable
    public String getPosterUrl(Context context) {
        if (poster != null && !poster.isEmpty()) {
            return DOWNLOADING_POSTER + poster;
        }
        return null;
    }

    @Nullable
    public String getBackdropUrl(Context context) {
        if (backdrop != null && !backdrop.isEmpty()) {
            return  DOWNLOADING_BACKDROP + backdrop;
        }
        return null;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeString(title);
        out.writeString(poster);
        out.writeString(overview);
        out.writeString(voteAverage);
        out.writeString(releaseDate);
        out.writeString(backdrop);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Movie(Parcel in) {
        id = in.readLong();
        title = in.readString();
        poster = in.readString();
        overview = in.readString();
        voteAverage = in.readString();
        releaseDate = in.readString();
        backdrop = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
