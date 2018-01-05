package com.dly.app.pojo;

import java.util.Date;

public class TPush {
    private Integer id;

    private String body;

    private Date date;

    private String title;
    TPush(){
    	
    }
    public TPush(String body,String title){
    	
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body == null ? null : body.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }
}