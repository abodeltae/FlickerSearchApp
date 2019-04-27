package com.nazeer.flickerproject.DataLayer.repo;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.models.PhotoListResponse;

public interface PhotosRepoClient {
	void getPhotos(long page, String query, SuccessFailureCallBack<PhotoListResponse> callBack);
}
