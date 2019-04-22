package com.nazeer.flickerproject.DataLayer.repo;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.models.PhotoListResponse;

import java.util.List;

public interface RecipeRepo {
	void getPhotos(int page, String query, SuccessFailureCallBack<PhotoListResponse> callBack);
}
