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
    private int farm;

    public Photo(String id, String serverId, int farm, String secret) {
        this.id = id;
        this.serverId = serverId;
        this.farm = farm;
        this.secret = secret;
    }

    private Photo(Parcel in) {
        id = in.readString();
        serverId = in.readString();
        farm = in.readInt();
        secret = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(serverId);
        dest.writeInt(farm);
        dest.writeString(secret);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public  String getUrl (){
        return String.format("https://farm%s.staticflickr.com/%s/%s_%s.jpg", farm, serverId, id, secret);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        if (farm != photo.farm) return false;
        if (id != null ? !id.equals(photo.id) : photo.id != null) return false;
        if (serverId != null ? !serverId.equals(photo.serverId) : photo.serverId != null)
            return false;
        return secret != null ? secret.equals(photo.secret) : photo.secret == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (serverId != null ? serverId.hashCode() : 0);
        result = 31 * result + (secret != null ? secret.hashCode() : 0);
        result = 31 * result + farm;
        return result;
    }
}
