package com.devforyou.onlineunivers.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.devforyou.onlineunivers.Converters.DateConverter;

import java.util.Date;

@Entity
public class Course {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public  String F_id;

    public boolean use;

    public String title;

    public String user_id;

    public  String Description;

    public int people_value;

    public String category;

    public int max_people_value;

    public String  what_can_learn;

    public float rating;


    @TypeConverters(DateConverter.class)
    public Date date;

    public  String HtmlText;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] image;






}
