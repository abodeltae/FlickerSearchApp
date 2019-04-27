package com.nazeer.flickerproject.DataLayer.models;

import java.util.List;

public class PhotoListResponse {
    private long totalPages;
    private long page;
    private List<Photo> photos;

    public PhotoListResponse(long pages, long page, List<Photo> photos) {
        this.totalPages = pages;
        this.page = page;
        this.photos = photos;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public long getPage() {
        return page;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhotoListResponse response = (PhotoListResponse) o;

        if (totalPages != response.totalPages) return false;
        if (page != response.page) return false;
        return photos != null ? photos.equals(response.photos) : response.photos == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (totalPages ^ (totalPages >>> 32));
        result = 31 * result + (int) (page ^ (page >>> 32));
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        return result;
    }
}
