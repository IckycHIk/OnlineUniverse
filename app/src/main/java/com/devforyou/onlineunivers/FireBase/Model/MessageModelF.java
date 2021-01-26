package com.devforyou.onlineunivers.FireBase.Model;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.firebase.Timestamp;

public class MessageModelF  {

    private String id;
    private String user_id;
    private String user_name;
    private String room_id;
    private String text;
    private Timestamp time;


    public MessageModelF() {
    }

    public MessageModelF(String id, String user_id, String user_name, String room_id, String text, Timestamp time) {
        this.id = id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.room_id = room_id;
        this.text = text;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
