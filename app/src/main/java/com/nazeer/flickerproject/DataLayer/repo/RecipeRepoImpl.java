package com.nazeer.flickerproject.DataLayer.repo;


import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.models.PhotoListResponse;

import java.util.List;

public class RecipeRepoImpl implements RecipeRepo {

	private  RecipeRepoClient remoteRecipeRepoClient;

	public RecipeRepoImpl(RecipeRepoClient remote ){
		this.remoteRecipeRepoClient = remote;
	}

	@Override
	public void getPhotos(int page, String query, SuccessFailureCallBack<PhotoListResponse> callBack) {
		remoteRecipeRepoClient.getRecipes(page,query,callBack);
	}
}