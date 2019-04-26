package com.nazeer.flickerproject.DataLayer.JsonProcessors;

import com.nazeer.flickerproject.DataLayer.models.Photo;

import org.json.JSONObject;

class PhotoParser {

    static Photo process(JSONObject jsonObject) {
        String id = JsonUtils.getString(jsonObject, "id");
        String serverId = JsonUtils.getString(jsonObject, "server");
        String farm = JsonUtils.getString(jsonObject, "farm");
        String secret = JsonUtils.getString(jsonObject,"secret");
        return new Photo(id,serverId,farm,secret);
    }
}
