package com.example.fengxinlin.myapplication.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fengxinlin on 11/22/16.
 */
public class Trailer implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;
    @SerializedName("site")
    private String site;
    @SerializedName("size")
    private String size;

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getTrailUrl() {
        return "http://www.youtube.com/watch?v=" + key;
    }

    private Trailer(Parcel in) {
        id = in.readString();
        key = in.readString();
        name = in.readString();
        site = in.readString();
        size = in.readString();
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(key);
        out.writeString(name);
        out.writeString(site);
        out.writeString(size);
    }
}
