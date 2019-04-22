package com.nazeer.flickerproject.DataLayer.JsonProcessors;

import com.nazeer.flickerproject.DataLayer.models.Photo;

import org.json.JSONException;
import org.json.JSONObject;

public class PhotoParser {

    public static Photo process(JSONObject jsonObject)  {
        String id = JsonUtils.getString(jsonObject, "id");
        String serverId = JsonUtils.getString(jsonObject, "server");
        int farm = JsonUtils.getInt(jsonObject, "farm");
        String secret = JsonUtils.getString(jsonObject,"secret");
        return new Photo(id,serverId,farm,secret);
    }
}
