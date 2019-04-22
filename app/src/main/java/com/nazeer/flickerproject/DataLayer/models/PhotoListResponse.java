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
}
