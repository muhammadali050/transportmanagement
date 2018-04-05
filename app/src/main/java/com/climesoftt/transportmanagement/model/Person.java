package com.climesoftt.transportmanagement.model;

import android.provider.Contacts;

/**
 * Created by AtoZ on 3/20/2018.
 */

public class Person {
    private String id = "";
    private String name = "";
    private String phone = "";
    private String address = "";
    private String image = "";
    private String email = "";
    private String password = "";
    private String accountType = "";

    public Person()
    {

    }

    public Person(String id, String name,String phone,String address,String img)
    {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.image = img;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
