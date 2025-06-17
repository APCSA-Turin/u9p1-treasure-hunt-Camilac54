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

            int newPlayerX = player.getX();
            int newPlayerY = player.getY();

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
                // Get the sprite at the potential new location
                Sprite targetSprite = grid.getGrid()[size - 1 - newPlayerY][newPlayerX];
                player.interact(size, userInput, totalTreasures, targetSprite);

                boolean playerCanMove = true; // Assume player can move by default

                if (targetSprite instanceof Treasure) {
                    // Player collects treasure, replaces treasure with Dot, and moves
                    // The treasure object should be replaced by a dot on the grid.
                    grid.getGrid()[size - 1 - newPlayerY][newPlayerX] = new Dot(newPlayerX, newPlayerY); // Treasure becomes a Dot
                } else if (targetSprite instanceof Enemy) {
                    // Player encounters an enemy. Player takes damage.
                    // Player does NOT move onto the enemy's spot.
                    playerCanMove = false; // Player stays at old position
                    // Message already printed by player.interact or can be added here
                }
                // If targetSprite is Trophy or Dot, player just moves normally.

                if (playerCanMove) {
                    grid.placeSprite(player, userInput);
                }
            } else {
                System.out.println("You cannot move outside the grid boundaries!");
            }
            // scanner.close();
        }
    }

    public void initialize(){
        grid = new Grid(size);
        player = new Player(0,0);
        grid.placeSprite(player); // Player starts at (0,0)

        Random rand = new Random();

        // Place Enemies
        int numEnemies = size / 3;
        enemies = new Enemy[numEnemies];
        for (int i = 0; i < numEnemies; i++) {
            int enemyX, enemyY;
            do {
                enemyX = rand.nextInt(size);
                enemyY = rand.nextInt(size);
            } while (isOccupied(enemyX, enemyY)); // Use isOccupied to check if spot is free
            enemies[i] = new Enemy(enemyX, enemyY);
            grid.placeSprite(enemies[i]);
        }

        // Place Treasures
        int numTreasures = size / 2; // Example: 5 treasures for size 10 grid
        treasures = new Treasure[numTreasures];
        totalTreasures = numTreasures; // Store total for win condition
        for (int i = 0; i < numTreasures; i++) {
            int treasureX, treasureY;
            do {
                treasureX = rand.nextInt(size);
                treasureY = rand.nextInt(size);
            } while (isOccupied(treasureX, treasureY)); // Use isOccupied
            treasures[i] = new Treasure(treasureX, treasureY);
            grid.placeSprite(treasures[i]);
        }

        // Place Trophy
        int trophyX, trophyY;
        do {
            trophyX = rand.nextInt(size); // Randomly place trophy
            trophyY = rand.nextInt(size);
        } while (isOccupied(trophyX, trophyY)); // Use isOccupied
        trophy = new Trophy(trophyX, trophyY);
        grid.placeSprite(trophy);

        //to test, create a player, trophy, grid, treasure, and enemies. Then call placeSprite() to put them on the grid
   
    }

    private boolean isOccupied(int x, int y) {
        // Convert (x,y) to grid (row,col)
        int row = size - 1 - y;
        int col = x;

        // Boundary check (though random.nextInt(size) should keep it in bounds)
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return true; // Consider out-of-bounds as occupied to prevent placement
        }

        Sprite s = grid.getGrid()[row][col];
        return !(s instanceof Dot); // It's occupied if it's not a Dot
    }


    public static void main(String[] args) {
        Scanner setupScanner = new Scanner(System.in);
        int gameSize = 0;
        boolean validSize = false;

        while (!validSize) {
            System.out.print("Enter grid size (e.g., 10 for a 10x10 grid): ");
            try {
                gameSize = setupScanner.nextInt();
                if (gameSize > 2 && gameSize <= 20) { // Example constraint: min 3x3, max 20x20
                    validSize = true;
                } else {
                    System.out.println("Grid size must be between 3 and 20.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                setupScanner.next(); // Consume the invalid input
            }
        }

        Game game = new Game(gameSize);
    }
}