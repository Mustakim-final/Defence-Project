package com.example.finddoctor.Model;

public class Emergency_prescription {
    private String imageUrl,prescription,name,information,day,meet,myId,userId,img_pres;

    public Emergency_prescription() {
    }

    public Emergency_prescription(String imageUrl, String prescription, String name, String information, String day, String meet, String myId, String userId,String img_pres) {
        this.imageUrl = imageUrl;
        this.prescription = prescription;
        this.name = name;
        this.information = information;
        this.day = day;
        this.meet = meet;
        this.myId = myId;
        this.userId = userId;
        this.img_pres=img_pres;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMeet() {
        return meet;
    }

    public void setMeet(String meet) {
        this.meet = meet;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImg_pres() {
        return img_pres;
    }

    public void setImg_pres(String img_pres) {
        this.img_pres = img_pres;
    }
}
