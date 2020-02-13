package com.example.drawmap.Models;

import java.io.Serializable;

public class Coordinate implements Serializable {

    private long id;
    private long idDrawing;
    private float x;
    private float y;

    public Coordinate(){

    }

    public Coordinate(float x, float y){
        this.x = x;
        this.y = y;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdDrawing() {
        return idDrawing;
    }

    public void setIdDrawing(long idDrawing) {
        this.idDrawing = idDrawing;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
