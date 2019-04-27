package com.nazeer.flickerproject.photosearch;

import android.os.Parcel;
import android.os.Parcelable;

import com.nazeer.flickerproject.DataLayer.models.Photo;

import java.util.ArrayList;

class PersistenceState implements Parcelable {
    public static final Creator<PersistenceState> CREATOR = new Creator<PersistenceState>() {
        @Override
        public PersistenceState createFromParcel(Parcel in) {
            return new PersistenceState(in);
        }

        @Override
        public PersistenceState[] newArray(int size) {
            return new PersistenceState[size];
        }
    };
    final ArrayList<Photo> photos;
    final long currentPage;
    final long totalPages;
    final String currentQuery;

    PersistenceState(ArrayList<Photo> photos, long currentPage, long totalPages, String currentQuery) {
        this.photos = photos;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.currentQuery = currentQuery;
    }

    PersistenceState(Parcel in) {
        photos = in.createTypedArrayList(Photo.CREATOR);
        currentPage = in.readLong();
        totalPages = in.readLong();
        currentQuery = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(photos);
        dest.writeLong(currentPage);
        dest.writeLong(totalPages);
        dest.writeString(currentQuery);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersistenceState state = (PersistenceState) o;

        if (currentPage != state.currentPage) return false;
        if (totalPages != state.totalPages) return false;
        if (photos != null ? !photos.equals(state.photos) : state.photos != null) return false;
        return currentQuery != null ? currentQuery.equals(state.currentQuery) : state.currentQuery == null;
    }

    @Override
    public int hashCode() {
        int result = photos != null ? photos.hashCode() : 0;
        result = 31 * result + (int) (currentPage ^ (currentPage >>> 32));
        result = 31 * result + (int) (totalPages ^ (totalPages >>> 32));
        result = 31 * result + (currentQuery != null ? currentQuery.hashCode() : 0);
        return result;
    }
}
