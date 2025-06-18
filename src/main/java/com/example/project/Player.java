package com.example.project;

//DO NOT DELETE ANY METHODS BELOW
public class Player extends Sprite { //child of Sprite
    private int treasureCount;
    private int numLives;
    private boolean win;

    public Player(int x, int y) { //set treasureCount = 0 and numLives = 2 
        super(x, y); // since player extends Sprite, i used super
        treasureCount = 0;
        numLives = 2;
        win = false;
    }

    public int getTreasureCount(){return treasureCount;}
    public int getLives(){return numLives;}
    public boolean getWin(){return win;}
  
    // move method should override parent class, sprite
    public void move(String direction) { // move the (x,y) coordinates of the player
        if (direction.equals("w")) { // moves up
            setY(getY() + 1); // alters y coordinate, increases
        } else if (direction.equals("a")) { // moves left 
            setX(getX() - 1); // alters x coordinate, decreases
        } else if (direction.equals("s")) { // moves down
            setY(getY() - 1); // alters y coordinate, decreases
        } else if (direction.equals("d")) { // moves right
            setX(getX() + 1); // alters x coordinate, increases
        }
    }

    public void interact(int size, String direction, int numTreasures, Object obj) { // interact with an object in the position you are moving to 
    //numTreasures is the total treasures at the beginning of the game
        if (obj instanceof Trophy) { // first check if the player can win
            if (treasureCount == numTreasures) {
                win = true;
            }
        } else if (obj instanceof Treasure) { // if the player can't win yet, check if the item they're "collecting" is treasure
            treasureCount++;
        } else if (obj instanceof Enemy) { // lastly, check if player is hitting an enemy
            numLives--;
        }
    }

    public boolean isValid(int size, String direction){ //check grid boundaries
        // return false;
        int newX = getX();
        int newY = getY();

        // checks with WASD the user inputted, and then perform a change to the X or Y accordingly
        if (direction.equals("w")) {
            newY += 1;
        } else if (direction.equals("s")) {
            newY -= 1;
        } else if (direction.equals("a")) {
            newX -= 1;
        } else if (direction.equals("d")) {
            newX += 1;
        } else {
            return false;
        }

        return newX >= 0 && newX < size && newY >= 0 && newY < size; // checks if new position is valid to place and returns if true or not
    }

    @Override
    public String getCoords(){ //returns "Player:"+coordinates
        return "Player:" + super.getCoords(); // Overrides method from Sprite to present coords
    }

    @Override
    public String getRowCol(int size){ //return "Player:"+row col
        return "Player:[" + (size - 1 - getY() + "][" + getX() + "]"); // Overrides method from Sprite to present grid row and col
    }

    @Override
    public String toString() {
        return "🤠";
    }
}