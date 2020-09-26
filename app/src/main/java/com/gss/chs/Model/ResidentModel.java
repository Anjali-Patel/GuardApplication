package com.gss.chs.Model;

public class ResidentModel {
    String name;
    String date;
    String flat_number;
    String wing_name;
String mailingAddress,AllocatedWork;

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getAllocatedWork() {
        return AllocatedWork;
    }

    public void setAllocatedWork(String allocatedWork) {
        AllocatedWork = allocatedWork;
    }

    public String getResident_photo() {
        return resident_photo;
    }

    public void setResident_photo(String resident_photo) {
        this.resident_photo = resident_photo;
    }

    String resident_photo;

    public String getWing_name() {
        return wing_name;
    }

    public void setWing_name(String wing_name) {
        this.wing_name = wing_name;
    }

    public String getFlat_number() {
        return flat_number;
    }

    public void setFlat_number(String flat_number) {
        this.flat_number = flat_number;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    String mobile_number;
    String inTime;
    String outTime;
    String purpose;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
