package com.example.project;

//DO NOT DELETE ANY METHODS BELOW
public class Player extends Sprite {
    private int treasureCount;
    private int numLives;
    private boolean win;

    public Player(int x, int y) { //set treasureCount = 0 and numLives = 2 
        super(x, y); // since player extends Sprite, i used super
        treasureCount = 0;
        numLives = 2;
    }


    public int getTreasureCount(){return treasureCount;}
    public int getLives(){return numLives;}
    public boolean getWin(){return win;}

  
    //move method should override parent class, sprite
    public void move(String direction) { //move the (x,y) coordinates of the player
        if (direction.equals("w")) { // moves up
            setY(getY() - 1); // alters y coordinate
        } else if (direction.equals("a")) { // moves left 
            setX(getX() - 1); // alters x coordinate
        } else if (direction.equals("s")) { // moves down
            setY(getY() + 1); // alters y coordinate
        } else if (direction.equals("d")) { // moves right
            setX(getX() + 1); // alters x coordinate
        } else {
            System.out.println("That's not a movement, silly!"); // invalid input message
        }
    }


    public void interact(int size, String direction, int numTreasures, Object obj) { // interact with an object in the position you are moving to 
    //numTreasures is the total treasures at the beginning of the game
        
    }


    public boolean isValid(int size, String direction){ //check grid boundaries
        return false;
    }
}