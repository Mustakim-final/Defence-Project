package com.example.finddoctor.Model;

public class SerialInfo {
    private String name,district,sub_District,localAddress,illness,phone;

    public SerialInfo() {
    }

    public SerialInfo(String name, String district, String sub_District, String localAddress, String illness, String phone) {
        this.name = name;
        this.district = district;
        this.sub_District = sub_District;
        this.localAddress = localAddress;
        this.illness = illness;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSub_District() {
        return sub_District;
    }

    public void setSub_District(String sub_District) {
        this.sub_District = sub_District;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

