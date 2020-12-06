package com.example.androidfinalproject.model;

// this class contains information for each place.
public class Place {
    private String id, name, address, phone, category;
    int hasInfo;

    public Place(String id, String name) {
        this.id = id;
        this.name = name;
        hasInfo = 0;
    }

    public Place(String id, String name, String address, String phone, String category, int hasInfo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.category = category;
        this.hasInfo = hasInfo;
    }
// getters and setters

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getHasInfo() {
        return hasInfo;
    }

    public void setHasInfo(int hasInfo) {
        this.hasInfo = hasInfo;
    }


}
