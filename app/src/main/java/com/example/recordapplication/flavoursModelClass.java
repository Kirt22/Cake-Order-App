package com.example.recordapplication;

public class flavoursModelClass {
    int id;
    String flavour;

    public flavoursModelClass(int id, String flavour) {
        this.id = id;
        this.flavour = flavour;
    }

    @Override
    public String toString() {
        return "flavoursModelClass{" +
                "id=" + id +
                ", flavour='" + flavour + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }
}