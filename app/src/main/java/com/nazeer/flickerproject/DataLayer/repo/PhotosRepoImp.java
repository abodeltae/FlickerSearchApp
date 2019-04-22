package com.nazeer.flickerproject.DataLayer.repo;


import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.models.PhotoListResponse;

public class PhotosRepoImp implements PhotosRepo {

	private PhotosRepoClient remoteRecipeRepoClient;

	public PhotosRepoImp(PhotosRepoClient remote ){
		this.remoteRecipeRepoClient = remote;
	}

	@Override
	public void getPhotos(long page, String query, SuccessFailureCallBack<PhotoListResponse> callBack) {
		remoteRecipeRepoClient.getPhotos(page,query,callBack);
	}
}