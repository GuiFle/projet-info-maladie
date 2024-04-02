package tp.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import tp.common.human;
import utility.MersenneTwister;
import java.util.ArrayList;


/**
 * The map class represents the grid for the simulation where individuals (humans) are placed and move.
 * It holds the individuals on a 2D grid and processes their status updates.
 */
public class map {

    // The size of the simulation grid
    public int mapSize;
    // List of humans awaiting to be processed
    public ArrayList<human> buddyArrayWaiting;
    // List of humans that have been processed
    public ArrayList<human> buddyArrayTreated;

    // A 2D grid that holds lists of humans at each grid location
    public ArrayList<human>[][] grid;

    /**
     * Constructor for the map class.
     * It initializes the grid with the size of m x m.
     * @param m The dimension of the grid.
     */
    @SuppressWarnings("unchecked")
    public map(int m) {
        this.mapSize = m;
        this.grid = new ArrayList[m][m];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < m; j++) {
                this.grid[i][j] = new ArrayList<>();
            }
        }
        this.buddyArrayWaiting = new ArrayList<>();
        this.buddyArrayTreated = new ArrayList<>();
    }

    /**
     * Updates the grid by moving humans and changing their states based on the simulation rules.
     * @param ran An instance of MersenneTwister for random number generation.
     */
    public void update(MersenneTwister ran){
        // Processing all humans in the waiting list
        for (int n = this.buddyArrayWaiting.size(); n > 0; n--) {
            int ranId = ran.nextInt(n);
            human buddy = this.buddyArrayWaiting.get(ranId);

            // Generate new random position for the human
            int x = ran.nextInt(mapSize);
            int y = ran.nextInt(mapSize);

            move(buddy, x, y); // Move the human to the new position

            // Check and update health state
            if ("S".equals(buddy.get_status())){
                double p = 1 - Math.exp(-0.5 * infectedNeighbours(buddy));
                if (ran.nextDouble() < p) {
                    buddy.becomeExposed();
                } else {
                    buddy.update();
                }
            } else {
                buddy.update();
            }

            // Move the human from the waiting list to the treated list
            this.buddyArrayTreated.add(buddy);
            this.buddyArrayWaiting.remove(ranId);
        }

        // Swap the lists for the next iteration
        ArrayList<human> tmp = this.buddyArrayTreated;
        this.buddyArrayTreated = this.buddyArrayWaiting;
        this.buddyArrayWaiting = tmp;
    }

    /**
     * Moves a human to a new location on the grid.
     * @param buddy The human to be moved.
     * @param x The new horizontal coordinate.
     * @param y The new vertical coordinate.
     */
    private void move(human buddy, int x, int y){
        // Remove the human from their old location and add them to the new one
        grid[buddy.x][buddy.y].remove(buddy);
        grid[x][y].add(buddy);
        buddy.changePos(x, y);
    }

    /**
     * Counts the number of infected neighbours around a given human.
     * @param buddy The human for whom to count the infected neighbours.
     * @return The number of infected neighbours.
     */
    private int infectedNeighbours(human buddy){
        int nbInfected = 0;
        // Check all surrounding cells for infected humans
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                int x = Math.floorMod(buddy.x - 1 + i, mapSize);
                int y = Math.floorMod(buddy.y - 1 + j, mapSize);
                for (human bud : grid[x][y]) {
                    if ("I".equals(bud.get_status())) {
                        nbInfected++;
                    }
                }
            }
        }
        return nbInfected;
    }

    /**
     * Adds a specified number of humans with an initial state to the grid.
     * @param ran An instance of MersenneTwister for random number generation.
     * @param nbHuman The number of humans to add.
     * @param state The initial state of the humans.
     */
    public void addAllHumans(MersenneTwister ran, int nbHuman, String state){
        // Add new humans to the waiting list
        for(int i = 0; i < nbHuman; i++){
            human buddy = new human(state, ran);
            buddyArrayWaiting.add(buddy);
        }
        // Place humans on the grid
        for (human budd : buddyArrayWaiting) {
            addHuman(ran.nextInt(mapSize), ran.nextInt(mapSize), budd);
        }
    }

    /**
     * Prints the human density of each cell on the grid to the console for debugging.
     */
    public void debugMap(){
        for(int i = 0; i < mapSize; i++){
            for(int j = 0; j < mapSize; j++){
                System.out.print(grid[i][j].size() + " ");
            }
            System.out.println();
        }
    }

    /**
     * Returns a string representing the global status of the population for debugging purposes.
     * @return A comma-separated string of counts for each status in the population.
     */
    public String debugGlobalStatus(){
        int s = 0; // Count of susceptible humans
        int e = 0; // Count of exposed humans
        int i = 0; // Count of infected humans
        int r = 0; // Count of recovered humans

        // Count humans in each state
        for (human buddy : buddyArrayWaiting) {
            String state = buddy.get_status();
            switch (state) {
                case "S": s++; break;
                case "E": e++; break;
                case "I": i++; break;
                case "R": r++; break;
                default: System.out.println("Error: Unknown state"); break;
            }
        }
        return s + "," + e + "," + i + "," + r;
    }

    /**
     * Adds a human to a specific grid position (x, y).
     * @param x The horizontal grid coordinate to place the human.
     * @param y The vertical grid coordinate to place the human.
     * @param buddy The human to be added to the grid.
     */
    public void addHuman(int x, int y, human buddy) {
        grid[x][y].add(buddy); // Adds the human to the specified cell
        buddy.changePos(x, y);  // Updates the human's position
    }
}