package com.taxtelecom.arinamurasheva.addressbook;

import java.util.List;

public class ItemsGroup<T> {
    private List<T> items;

    private String name;

    private boolean expanded;

    public ItemsGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
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
}
