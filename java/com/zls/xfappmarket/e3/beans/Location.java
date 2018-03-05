package com.zls.xfappmarket.e3.beans;

/**
 * Created by oop on 2018/2/11.
 */

public class Location {

    private float x;
    private float y;

    public Location(float x, float y){
        this.x = x;
        this.y = y;
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
