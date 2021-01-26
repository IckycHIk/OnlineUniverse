package com.devforyou.onlineunivers.FireBase.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class TestModelF {

    private String id;
    private String title;
    private String description;
    private String course_id;
    private long time;
    private boolean see_answer;

    public TestModelF() {
    }

    public TestModelF(String id, String title, String description, String course_id, long time, boolean see_answer) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.course_id = course_id;
        this.time = time;
        this.see_answer = see_answer;
    }

    public boolean isSee_answer() {
        return see_answer;
    }

    public void setSee_answer(boolean see_answer) {
        this.see_answer = see_answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}