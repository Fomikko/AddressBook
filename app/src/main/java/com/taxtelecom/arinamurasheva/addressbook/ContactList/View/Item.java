package com.taxtelecom.arinamurasheva.addressbook.ContactList.View;

import androidx.annotation.NonNull;

import java.util.List;

public class Item {

    private List<Item> items;

    private boolean expanded;

    private String name;

    private int nestingLevel;

    public Item(String name) {

        this.name = name;

    }

    public Item(String name, int nestingLevel) {
        this.name = name;
        this.nestingLevel = nestingLevel;
    }

    public void setNestingLevel(int nestingLevel) {
        this.nestingLevel = nestingLevel;
    }

    public int getNestingLevel() {
        return this.nestingLevel;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public void expand() {
        this.expanded = true;
    }

    public void collapse() {
        this.expanded = false;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
