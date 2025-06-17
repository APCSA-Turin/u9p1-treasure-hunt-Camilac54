package com.example.project;

//DO NOT DELETE ANY METHODS BELOW
public class Grid{
    private Sprite[][] grid;
    private int size;

    public Grid(int size) { //initialize and create a grid with all DOT objects
        this.size = size; // setting the size
        grid = new Sprite[size][size]; // creating an empty grid with declared size
        for (int row = 0; row < size; row ++) { // filling in the grid with Dot objects, row first
            for (int col = 0; col < size; col ++) { // iterating through columns inside rows
                grid[row][col] = new Dot(); // adding the dots
            }
        }
    }
 
    public Sprite[][] getGrid(){return grid;}


    public void placeSprite(Sprite s){ // place sprite in new spot
        int row = size - 1 - s.getY();
        int col = s.getX();
        if (row >= 0 && row < size && col >= 0 && col < size) { // checks if the new place is a valid spot on the grid
            grid[row][col] = s;
        }
    }

    public void placeSprite(Sprite s, String direction) { // place sprite in a new spot based on direction
        int oldY = s.getY();
        int oldX = s.getX();
        
        if (direction.equals("w")) { // moves up
            oldY = s.getY() - 1; // obtains previous movement, which was one down
        } else if (direction.equals("s")) { // moves down
            oldY = s.getY() + 1; // obtains previous movement, which was one up
        } else if (direction.equals("a")) { // moves left
            oldX = s.getX() + 1; // obtains previous momvent, which was one right 
        } else if (direction.equals("d")) { // moves right
            oldX = s.getX() - 1; // obtains previous movement, which was one left
        }

        // grid variables with oldY and oldX
        int oldGridRow = size - 1 - oldY;
        int oldGridCol = oldX;

        // if the oldGrid rows and columns are valid places, place a dot there
        if (oldGridRow >= 0 && oldGridRow < size && oldGridCol >= 0 && oldGridCol < size) {
            grid[oldGridRow][oldGridCol] = new Dot();
        }

        // new grid variables for new place of sprite
        int newGridRow = size - 1 - s.getY(); 
        int newGridCol = s.getX();
        grid[newGridRow][newGridCol] = s; // placing new sprite
    }


    public void display() { // print out the current grid to the screen 
        for (int row = 0; row < size; row ++) { // iterating through rows
            for (int col = 0; col < size; col ++) { // iterating through columns
                System.out.println(grid[row][col].toString() + " "); // placing specific index with space
            }
            System.out.println();
        }
    }
    
    public void gameover(){ //use this method to display a loss
        System.out.println("Game over. You lost all of your lives.");
    }

    public void win(){ //use this method to display a win 
        System.out.println("You win! You got all the treasure!");
    }
}