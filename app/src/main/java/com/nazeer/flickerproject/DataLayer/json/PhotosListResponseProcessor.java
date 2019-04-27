package com.nazeer.flickerproject.DataLayer.json;

import com.nazeer.flickerproject.DataLayer.models.Photo;
import com.nazeer.flickerproject.DataLayer.models.PhotoListResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotosListResponseProcessor implements JsonProcessor<PhotoListResponse> {

	@Override
	public PhotoListResponse process(String jsonString) throws JSONException {
		JSONObject jsonObject =new JSONObject(jsonString);
		return process(jsonObject.getJSONObject("photos"));
	}

	private PhotoListResponse process(JSONObject jsonObject) throws JSONException {
		long pages = jsonObject.getLong("pages");
		long page = jsonObject.getLong("page");
		JSONArray jsonArray = jsonObject.getJSONArray("photo");
		ArrayList<Photo> photos = new ArrayList<>(jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			photos.add(PhotoParser.process(jsonArray.getJSONObject(i)));
		}
		return new PhotoListResponse(pages,page,photos);

	}



}
