package com.devforyou.onlineunivers.FireBase.Model;

import java.util.Date;

public class UserModelF {

    private String id;
    private String name;
    private String courses;
    //0=deletet;1=active_user ;2=tuter;3 = admin;
    private int role;
    private float rating;
    private String img_url;

    public UserModelF(){

    }

    public UserModelF(String id, String name, String courses, int role, float rating, String img_url) {
        this.id = id;
        this.name = name;
        this.courses = courses;
        this.role = role;
        this.rating = rating;
        this.img_url = img_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
