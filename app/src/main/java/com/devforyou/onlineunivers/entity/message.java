package com.devforyou.onlineunivers.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class message {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String user_id;

    public  String text_message;



}
