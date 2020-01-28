package com.taxtelecom.arinamurasheva.addressbook.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Person implements Serializable {

    @SerializedName("ID")
    private String id;

    @SerializedName("Name")
    private String name;

    @SerializedName("Title")
    private String title;

    @SerializedName("Email")
    private String email;

    @SerializedName("Phone")
    private String phone;

    public Person(String id, String name, String title, String email, String phone) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.email = email;
        this.phone = phone;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
