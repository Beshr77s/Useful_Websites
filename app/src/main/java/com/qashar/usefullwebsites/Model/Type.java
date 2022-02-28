package com.qashar.usefullwebsites.Model;

public class Type {
    private String name;
    public boolean isSelected = false;

    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

