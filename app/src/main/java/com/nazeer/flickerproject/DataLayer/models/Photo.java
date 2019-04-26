package com.nazeer.flickerproject.DataLayer.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Parcelable {
   private String id;
    private String serverId;
    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
    private String secret;
    private String farm;

    public Photo(String id, String serverId, String farm, String secret) {
        this.id = id;
        this.serverId = serverId;
        this.farm = farm;
        this.secret = secret;
    }

    private Photo(Parcel in) {
        id = in.readString();
        serverId = in.readString();
        farm = in.readString();
        secret = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(serverId);
        dest.writeString(farm);
        dest.writeString(secret);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public  String getUrl (){
        return String.format("https://farm%s.staticflickr.com/%s/%s_%s.jpg", farm, serverId, id, secret);
    }


}
