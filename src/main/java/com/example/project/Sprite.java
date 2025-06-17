package com.example.project;

public class Sprite {
    private int x, y;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX(){ // returns the x value
        return x;
    }
    public int getY(){ // returns the y value
        return y;
    }

    public void setX(int x2){
        x = x2;
    }
    public void setY(int y2){
        y = y2;
    }

    public String getCoords(){ //returns the coordinates of the sprite ->"(x,y)"
        return "(" + getX() + "," + getY() + ")";
    }

    public String getRowCol(int size){ //returns the row and column of the sprite -> "[row][col]"
        return "[" + (size - 1 - getY()) + "][" + getX() + "]";
    }
    

    public void move(String direction) { //you can leave this empty
        // Default behavior (can be overridden by subclasses)
    }

    public void interact() { //you can leave this empty
        // Default behavior (can be overridden by subclasses)
    }
}