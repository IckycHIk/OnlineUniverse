package com.devforyou.onlineunivers.FireBase.Model;

import java.sql.Timestamp;

public class LessonsModelF {

    private String id;
    private String course_id;

  //  private Timestamp date;
    private String html_text;
    private String title;

public LessonsModelF(){

}

    public LessonsModelF(String id, String course_id, String html_text, String title) {
        this.id = id;
        this.course_id = course_id;
       // this.date = date;
        this.html_text = html_text;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }


    public String getHtml_text() {
        return html_text;
    }

    public void setHtml_text(String html_text) {
        this.html_text = html_text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
