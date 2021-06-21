package com.example.premiums.Models;


public class AdminUsers_model {

    private String name;

    private String phone;

    private String address;

    private String age;

    private String Url;

    public AdminUsers_model() {
    }

    public AdminUsers_model(String name, String phone, String address, String age, String url) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.age = age;
        this.Url = url;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
