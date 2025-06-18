package com.example.project;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Game{
    private Grid grid;
    private Player player;
    private Enemy[] enemies;
    private Treasure[] treasures;
    private Trophy trophy;
    private int size; 

    private int totalTreasures;

    public Game(int size){ //the constructor should call initialize() and play()
        this.size = size;
        this.totalTreasures = 0;
        initialize();
        play();
    }

    public static void clearScreen() { //do not modify
        try {
            final String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unix-based (Linux, macOS)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){ //write your game logic here
        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        while(true){
            try {
                Thread.sleep(100); // Wait for 1/10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clearScreen(); // Clear the screen at the beggining of the while loop

            System.out.println("Treasures Collected: " + player.getTreasureCount());
            System.out.println("Lives: " + player.getLives());
            grid.display();
            if (player.getWin()) {
                clearScreen();
                grid.win();
                System.out.println("You collected " + player.getTreasureCount() + " horse shoes!");
                break;
            }
     
            if (player.getLives() <= 0) {
                clearScreen();
                grid.gameover();
                break;
            }

            System.out.println("\nEnter direction (w/a/s/d to move, q to quit):");
            userInput = scanner.nextLine().toLowerCase();

            if (userInput.equals("q")) {
                System.out.println("Exiting game.....\n Goodbye!");
                break;
            }

            // checking valid input
            if (!userInput.equals("w") && !userInput.equals("a") && !userInput.equals("s") && !userInput.equals("d")) {
                System.out.println("Invalid input. Please use 'w', 'a', 's', 'd', or 'q'.");
                continue; 
            }

            // Player Variables 
            int newPlayerX = player.getX();
            int newPlayerY = player.getY();

            // Movement input checker 
            if (userInput.equals("w")) {
                newPlayerY += 1;
            } else if (userInput.equals("s")) {
                newPlayerY -= 1;
            } else if (userInput.equals("a")) {
                newPlayerX -= 1;
            } else if (userInput.equals("d")) {
                newPlayerX += 1;
            }

            // Check if the intended move is within bounds using player.isValid()
            if (player.isValid(size, userInput)) {

                Sprite targetSprite = grid.getGrid()[size - 1 - newPlayerY][newPlayerX]; // new sprite location
                player.interact(size, userInput, totalTreasures, targetSprite);

                boolean playerCanMove = true; 
                
                // Logic to check which Sprite the user might encounter
                if (targetSprite instanceof Treasure) { // checks if treasure
                    grid.getGrid()[size - 1 - newPlayerY][newPlayerX] = new Dot(newPlayerX, newPlayerY); // converts treasure into a Dot (clearing it)
                } else if (targetSprite instanceof Enemy) { // Player cannot move if a Dot is an enemy
                    playerCanMove = false; 
                }
                if (playerCanMove) { // player moves normally 
                    player.setX(newPlayerX);
                    player.setY(newPlayerY);

                    grid.placeSprite(player, userInput);
                }
            } else {
                System.out.println("You cannot move outside the grid boundaries!"); // out of boundaries 
            }
        }
    }

    public void initialize(){
        grid = new Grid(size);
        player = new Player(0,0);
        grid.placeSprite(player); // player starts at (0,0)

        Random rand = new Random();

        // Place Enemies
        int numEnemies = size / 3;
        enemies = new Enemy[numEnemies];
        for (int i = 0; i < numEnemies; i++) {  
            int enemyX = rand.nextInt(size);
            int enemyY = rand.nextInt(size);
            if (!(isOccupied(enemyX,enemyY))) {
                enemies[i] = new Enemy(enemyX, enemyY);
                grid.placeSprite(enemies[i]);
            }
        }

        // Place Treasures
        int numTreasures = size / 2; 
        treasures = new Treasure[numTreasures];
        totalTreasures = numTreasures;
        for (int i = 0; i < numTreasures; i++) { 
            // set x and y coorindates in random locations 
            int treasureX = rand.nextInt(size);
            int treasureY = rand.nextInt(size);
            if (!isOccupied(treasureX,treasureY)) {
                treasures[i] = new Treasure(treasureX, treasureY);
                grid.placeSprite(treasures[i]);
            }            
        }

        // Place Trophy
        int trophyX = rand.nextInt(size);
        int trophyY = rand.nextInt(size);
        if (!isOccupied(trophyX,trophyY)) {
            trophy = new Trophy(trophyX, trophyY);
            grid.placeSprite(trophy);
        }
        //to test, create a player, trophy, grid, treasure, and enemies. Then call placeSprite() to put them on the grid
    }

    // Method to check if enemies or treasures can be placed on specific areas of the map
    private boolean isOccupied(int x, int y) {
        int row = size - 1 - y;
        int col = x;

        if (row < 0 || row >= size || col < 0 || col >= size) { // checking if its kept in bounds
            return true;
        }
        Sprite s = grid.getGrid()[row][col];
        return !(s instanceof Dot); // essentially, it's occupied if it's not a Dot
    }

    public static void main(String[] args) {
        Scanner setupScanner = new Scanner(System.in);
        int gameSize = 0;
        boolean validSize = false;

        while (!validSize) {
            System.out.print("Enter grid size (e.g., 10 for a 10x10 grid): ");
            try {
                gameSize = setupScanner.nextInt();
                if (gameSize > 2 && gameSize <= 20) { 
                    validSize = true;
                } else {
                    System.out.println("Grid size must be between 3 and 20.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                setupScanner.next(); 
            }
        }
        Game game = new Game(gameSize);
    }
}