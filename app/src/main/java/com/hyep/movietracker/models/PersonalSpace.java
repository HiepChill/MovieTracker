package com.hyep.movietracker.models;

public class PersonalSpace {

    private String name;

    private int number;

    private String color;

    private int iconId;

    public PersonalSpace(String name, int number, String color, int iconId) {
        this.name = name;
        this.number = number;
        this.color = color;
        this.iconId = iconId;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
