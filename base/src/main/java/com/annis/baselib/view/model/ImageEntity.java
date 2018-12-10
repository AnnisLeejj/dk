package com.annis.baselib.view.model;


public class ImageEntity {
    String id;
    String url;
    String type;
    String localpath;

    public ImageEntity(String url, String type) {
        this.url = url;
        this.type = type;
    }

//    public ImageEntity(String id, String url, String type) {
//        this.id = id;
//        this.url = url;
//        this.type = type;
//    }

    public ImageEntity(String id, String url, String localpath, String type) {
        this.id = id;
        this.url = url;
        this.type = type;
        this.localpath = localpath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public String getLocalpath() {
        return localpath;
    }

    public void setLocalpath(String localpath) {
        this.localpath = localpath;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}