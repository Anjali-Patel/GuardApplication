package com.gss.chs.Model;

public class VisitorModel {
    String name;
    String date;
    String mobile_number;
    String wing_name;
    String visitor_id;

    public String getVisitor_id() {
        return visitor_id;
    }

    public void setVisitor_id(String visitor_id) {
        this.visitor_id = visitor_id;
    }

    public String getWing_name() {
        return wing_name;
    }

    public void setWing_name(String wing_name) {
        this.wing_name = wing_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getUser_flatnumber() {
        return user_flatnumber;
    }

    public void setUser_flatnumber(String user_flatnumber) {
        this.user_flatnumber = user_flatnumber;
    }

    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
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

    String user_flatnumber;
    String in_time;
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
