package com.example.finddoctor.Model;

import java.util.Comparator;

public class Hospital {
    private String id,title,address,phone,hospital,info,ambulance;

    public Hospital() {
    }

    public Hospital(String id, String title, String address, String phone, String hospital,String info,String ambulance) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.phone = phone;
        this.hospital = hospital;
        this.info=info;
        this.ambulance=ambulance;
    }

    public static Comparator<Hospital> AmbulanceTitleCamparator=new Comparator<Hospital>() {
        @Override
        public int compare(Hospital p1, Hospital p2) {
            return p1.getTitle().compareTo(p2.getTitle());
        }
    };

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    public String getAmbulance() {
        return ambulance;
    }

    public void setAmbulance(String ambulance) {
        this.ambulance = ambulance;
    }
}
