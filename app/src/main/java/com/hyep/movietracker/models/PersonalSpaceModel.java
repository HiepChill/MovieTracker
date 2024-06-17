package com.hyep.movietracker.models;

public class PersonalSpaceModel {
    private String id;

    private String name;

    private int size;

    private int color;

    private int icon;

    public PersonalSpaceModel() {
    }

    public PersonalSpaceModel(String id, String name, int size, int color, int icon) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.color = color;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
