package com.devforyou.onlineunivers.FireBase.Model;


import com.google.firebase.Timestamp;

import java.util.Date;

public class ChatRoomModelF {

    private String id;
    private String Title;
    private String course_id;
    private Timestamp date;


    public ChatRoomModelF() {
    }

    public ChatRoomModelF(String id, String title, String course_id, Timestamp date) {
        this.id = id;
        Title = title;
        this.course_id = course_id;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
