package com.nazeer.flickerproject.DataLayer.repo;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.JsonProcessors.JsonProcessor;
import com.nazeer.flickerproject.DataLayer.models.PhotoListResponse;

public interface RecipeRepoClient {
	void getRecipes(int page, String query, SuccessFailureCallBack<PhotoListResponse> callBack);


}
