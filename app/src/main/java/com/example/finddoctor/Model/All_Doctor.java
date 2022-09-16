package com.example.finddoctor.Model;

public class All_Doctor {
    String name,information,age,gender,phone,address,id,username,gmail,imageUrl,status,day,fees,type,myId;

    public All_Doctor() {
    }

    public All_Doctor(String name, String information, String age, String gender, String phone, String address, String id, String username, String gmail, String imageUrl, String status, String day, String fees, String type,String myId) {
        this.name = name;
        this.information = information;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.id = id;
        this.username = username;
        this.gmail = gmail;
        this.imageUrl = imageUrl;
        this.status = status;
        this.day = day;
        this.fees = fees;
        this.type = type;
        this.myId=myId;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }
}
