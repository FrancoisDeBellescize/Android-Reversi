package com.example.francoisdebellescize.a02_reversi.Model;

/**
 * Created by francoisdebellescize on 03/04/2017.
 */

public class Vector2d {
    public int x;
    public int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void set(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void add(Vector2d add){
        this.x += add.x;
        this.y += add.y;
    }

    public void remove(Vector2d add){
        this.x -= add.x;
        this.y -= add.y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
