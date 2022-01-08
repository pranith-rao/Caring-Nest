package com.example.caringnest;

public class Model_user {
    String name, phone, location, id;

    public Model_user() {

    }

    public Model_user(String id, String name, String location, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
