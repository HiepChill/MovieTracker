package com.hyep.movietracker.models;

public class TagModel {
    private String id;
    private String name;
    private int size;
    private int color;

    public TagModel() {
    }

    public TagModel(String id, String name, int size, int color) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.color = color;
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
}
