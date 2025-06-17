package com.example.project;

//Enemy only need constructor and getCoords() getRowCol()
public class Enemy extends Sprite { //child  of Sprite
    
    public Enemy(int x, int y) {
        super(x, y);
    }


    //the methods below should override the super class 

    @Override
    public String getCoords(){ //returns "Enemy:"+coordinates
        return "Enemy:" + super.getCoords(); // uses method from Sprite
    }

    @Override
    public String getRowCol(int size){ //return "Enemy:"+row col
        return "Enemy:[" + (size - 1 - getY() + "][" + getX() + "]"); // uses more methods from Sprite to find row and col
    }
}