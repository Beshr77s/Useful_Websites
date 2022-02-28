package com.qashar.usefullwebsites.Model;

public class Model {
    private String title , details,url,type;

    public Model(String title, String details, String url,String type) {
        this.title = title;
        this.type = type;
        this.details = details;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
