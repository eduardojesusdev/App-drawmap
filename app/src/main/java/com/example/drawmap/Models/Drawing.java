package com.example.drawmap.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Drawing implements Serializable {

    private long id;
    private String name;
    private ArrayList<Coordinate> coordinates;

    public Drawing(){
        coordinates = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void addCoordinate(Coordinate coordinate) {
        this.coordinates.add(coordinate);
    }

    @Override
    public String toString(){
        return name;
    }
}
