package com.taxtelecom.arinamurasheva.addressbook.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Department implements Serializable {

    @SerializedName("ID")
    private String id;

    @SerializedName("Name")
    private String name;

    @SerializedName("Departments")
    private List<Department> mDepartments;

    @SerializedName("Employees")
    private List<Person> mEmployees;

    public Department(String id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Person> getEmployees() {
        return mEmployees;
    }

    public void setEmployees(List<Person> employees) {
        this.mEmployees = employees;
    }

    public List<Department> getDepartments() {
        return mDepartments;
    }

    public void setDepartments(List<Department> departments) {
        this.mDepartments = departments;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}