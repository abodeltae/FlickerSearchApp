package com.nazeer.flickerproject.DataLayer.json;

import org.json.JSONException;

public  interface JsonProcessor <T> {

	T process(String jsonString) throws JSONException;


}
