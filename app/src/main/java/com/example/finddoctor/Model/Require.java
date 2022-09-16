package com.example.finddoctor.Model;

public class Require {

    private String b_group,b_type,b_require,b_datetime,b_number,b_location;
    private String b_gift,b_description,id,imageUrl,name;


    public Require() {
    }

    public Require(String b_group, String b_type, String b_require, String b_datetime, String b_number, String b_location, String b_gift, String b_description, String id, String imageUrl, String name) {
        this.b_group = b_group;
        this.b_type = b_type;
        this.b_require = b_require;
        this.b_datetime = b_datetime;
        this.b_number = b_number;
        this.b_location = b_location;
        this.b_gift = b_gift;
        this.b_description = b_description;
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
    }


    public String getB_group() {
        return b_group;
    }

    public void setB_group(String b_group) {
        this.b_group = b_group;
    }

    public String getB_type() {
        return b_type;
    }

    public void setB_type(String b_type) {
        this.b_type = b_type;
    }

    public String getB_require() {
        return b_require;
    }

    public void setB_require(String b_require) {
        this.b_require = b_require;
    }

    public String getB_datetime() {
        return b_datetime;
    }

    public void setB_datetime(String b_datetime) {
        this.b_datetime = b_datetime;
    }

    public String getB_number() {
        return b_number;
    }

    public void setB_number(String b_number) {
        this.b_number = b_number;
    }

    public String getB_location() {
        return b_location;
    }

    public void setB_location(String b_location) {
        this.b_location = b_location;
    }

    public String getB_gift() {
        return b_gift;
    }

    public void setB_gift(String b_gift) {
        this.b_gift = b_gift;
    }

    public String getB_description() {
        return b_description;
    }

    public void setB_description(String b_description) {
        this.b_description = b_description;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
