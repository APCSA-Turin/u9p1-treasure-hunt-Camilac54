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



    public void placeSprite(Sprite s){ //place sprite in new spot
        int row = size - 1 - s.getY();
        int col = s.getX();
        grid[row][col] = new Dot();

        int newRow = size - 1 - s.getY();
        int newCol = s.getX();
        grid[newRow][newCol] = s;

    }

    public void placeSprite(Sprite s, String direction) { //place sprite in a new spot based on direction
        int oldRow = size - 1 - s.getY();
        int oldCol = s.getX();
    
        switch (direction) {
            case "w": s.setY(s.getY() + 1); break;
            case "s": s.setY(s.getY() - 1); break;
            case "a": s.setX(s.getX() - 1); break;
            case "d": s.setX(s.getX() + 1); break;
        }
    
        grid[oldRow][oldCol] = new Dot();
        int newRow = size - 1 - s.getY();
        int newCol = s.getX();
        grid[newRow][newCol] = s;
    }


    public void display() { //print out the current grid to the screen 
        for (int row = 0; row < size; row ++) {
            for (int col = 0; col < size; col ++) {
                System.out.println(grid[row][col].toString() + " ");
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