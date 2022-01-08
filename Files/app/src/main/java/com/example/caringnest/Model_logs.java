package com.example.caringnest;

public class Model_logs {
    String id, p_name, p_phone, p_location, a_name, a_phone, a_location, a_email, time;

    public Model_logs(){

    }

    public Model_logs(String id, String p_name, String p_phone, String p_location, String a_name, String a_phone, String a_location, String a_email, String time) {
        this.id = id;
        this.p_name = p_name;
        this.p_phone = p_phone;
        this.p_location = p_location;
        this.a_name = a_name;
        this.a_phone = a_phone;
        this.a_location = a_location;
        this.a_email = a_email;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_phone() {
        return p_phone;
    }

    public void setP_phone(String p_phone) {
        this.p_phone = p_phone;
    }

    public String getP_location() {
        return p_location;
    }

    public void setP_location(String p_location) {
        this.p_location = p_location;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public String getA_phone() {
        return a_phone;
    }

    public void setA_phone(String a_phone) {
        this.a_phone = a_phone;
    }

    public String getA_location() {
        return a_location;
    }

    public void setA_location(String a_location) {
        this.a_location = a_location;
    }

    public String getA_email() {
        return a_email;
    }

    public void setA_email(String a_email) {
        this.a_email = a_email;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
