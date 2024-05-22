package com.hyep.movietracker.models;

public class PersonalSpaceModel {

    private String name;

    private int number;

    private int color;

    private int icon;

    public PersonalSpaceModel(String name, int number, int color, int icon) {
        this.name = name;
        this.number = number;
        this.color = color;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
}
