package com.nazeer.flickerproject.DataLayer.models;

public class Photo {
   private String id;
    private String serverId;
    private int farm;
    private String secret;

    public Photo(String id, String serverId, int farm, String secret) {
        this.id = id;
        this.serverId = serverId;
        this.farm = farm;
        this.secret = secret;
    }

    public  String getUrl (){
        return String.format("https://farm%d.staticflickr.com/%s/%s_%s.jpg",farm,serverId,id,secret);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
