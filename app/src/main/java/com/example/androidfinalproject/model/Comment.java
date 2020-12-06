package com.example.androidfinalproject.model;

public class Comment {

    private String placeId;
    private String text;
    private String time;

    public Comment(String placeId, String text, String time) {
        this.time = time;
        this.placeId = placeId;
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
