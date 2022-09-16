package com.example.finddoctor.Model;

public class Appointment {
    private String imageUrl,name,meet,date,d_id,id;

    public Appointment() {
    }

    public Appointment(String imageUrl, String name, String meet, String date,String id,String d_id) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.meet = meet;
        this.date = date;
        this.id=id;
        this.d_id=d_id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeet() {
        return meet;
    }

    public void setMeet(String meet) {
        this.meet = meet;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
