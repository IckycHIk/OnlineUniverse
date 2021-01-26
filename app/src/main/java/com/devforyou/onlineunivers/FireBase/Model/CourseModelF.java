package com.devforyou.onlineunivers.FireBase.Model;

import java.util.Date;

public class CourseModelF {

  private String id;
    private String Title;
    private String user_id;
    private  String Description;
    private long people_value;
    private String material_ids;
    private String category;
            private long max_people_value;
    private String what_can_lear;
    private float rating;
    private Date date;
    private  String html_text;
    private String img_url;


    public CourseModelF(){

    }

    public CourseModelF(String id, String title, String user_id, String description, long people_value,
                       String material_ids,String category,
                        long max_people_value, String what_can_lear, float rating, Date date, String html_text, String img_url) {
        this.id = id;
        Title = title;
        this.user_id = user_id;
        Description = description;
        this.people_value = people_value;
        this.material_ids= material_ids;
        this.category = category;
        this.max_people_value = max_people_value;
        this.what_can_lear = what_can_lear;
        this.rating = rating;
        this.date = date;
        this.html_text = html_text;
        this.img_url = img_url;
    }

    public String getMaterial_ids() {
        return material_ids;
    }

    public void setMaterial_ids(String material_ids) {
        this.material_ids = material_ids;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public long getPeople_value() {
        return people_value;
    }

    public void setPeople_value(long people_value) {
        this.people_value = people_value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getMax_people_value() {
        return max_people_value;
    }

    public void setMax_people_value(long max_people_value) {
        this.max_people_value = max_people_value;
    }

    public String getWhat_can_lear() {
        return what_can_lear;
    }

    public void setWhat_can_lear(String what_can_learn) {
        this.what_can_lear = what_can_learn;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHtml_text() {
        return html_text;
    }

    public void setHtml_text(String html_text) {
        this.html_text = html_text;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
