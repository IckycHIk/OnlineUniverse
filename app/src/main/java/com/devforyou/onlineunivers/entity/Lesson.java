package com.devforyou.onlineunivers.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.devforyou.onlineunivers.Converters.DateConverter;

import java.util.Date;
import java.util.List;


@Entity
public class Lesson {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String user_id;

    public  String Description;

    public long course_id;
    @TypeConverters(DateConverter.class)
    public Date date;

    public  String HtmlText;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
   public byte[] image;


}
