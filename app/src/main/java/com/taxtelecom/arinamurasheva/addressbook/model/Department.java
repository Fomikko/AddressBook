package com.taxtelecom.arinamurasheva.addressbook.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class Department implements Serializable {
    private String id;
    private String name;
    private List<Department> mDepartments;
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