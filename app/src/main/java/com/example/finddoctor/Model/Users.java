package com.example.finddoctor.Model;

public class Users {
    private String username,id,imageUrl;
    private String phone,blood,nid,birthDate,gender,division,subdivision,district;

    public Users() {

    }

    public Users(String username, String id, String imageUrl, String phone, String blood, String nid, String birthDate, String gender, String division, String subdivision, String district) {
        this.username = username;
        this.id = id;
        this.imageUrl = imageUrl;
        this.phone = phone;
        this.blood = blood;
        this.nid = nid;
        this.birthDate = birthDate;
        this.gender = gender;
        this.division = division;
        this.subdivision = subdivision;
        this.district = district;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
