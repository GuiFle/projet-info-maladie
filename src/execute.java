package tp.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import utility.MersenneTwister;

/**
 * The execute class manages the initialization and execution of a disease
 * propagation simulation using a multi-agent system.
 */
public class execute {
    
    private MersenneTwister ran; // Random number generator using the Mersenne Twister algorithm.
    private map map; // Represents the 2D grid on which the simulation runs.


    /**
     * Constructor that initializes the simulation environment.
     * @param mapSize Size of the square grid for the simulation.
     * @param nbHumanSus Number of susceptible humans to be placed on the grid.
     * @param nbHumanIll Number of initially ill humans to be placed on the grid.
     */
    public execute(int mapSize, int nbHumanSus, int nbHumanIll, MersenneTwister ran){
        this.ran = ran; // Initializes the random generator for reproducibility.
        this.map = new map(mapSize); // Initializes the map with the specified size.
        map.addAllHumans(ran, nbHumanSus, "S"); // Adds susceptible humans to the map.
        map.addAllHumans(ran, nbHumanIll, "I"); // Adds ill humans to the map.
    }

    /**
     * Updates and displays a loading bar in the console.
     * @param currentProgress The current progress of the simulation.
     * @param total The total number of iterations for the simulation.
     */
    public static void updateLoadingBar(int currentProgress, int total) {
        int barLength = 50; // Fixed length for the loading bar.
        int progress = (int) ((float) currentProgress / total * barLength);
        StringBuilder progressBar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            progressBar.append(i < progress ? "=" : " ");
        }
        progressBar.append("] ");
        System.out.print("\r" + progressBar.toString() + (progress * 2) + "%");
    }
    
    /**
     * Writes an array of strings to a CSV file.
     * @param fileName The name of the CSV file to write to.
     * @param strings The array of strings to be written to the file.
     */
    public static void writeStringsToCSV(String fileName, String[] strings) {
        Path filePath = Paths.get(fileName);
        try {
            Files.createDirectories(filePath.getParent()); // Ensures the directory exists.
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                for (int i = 0; i < strings.length; i++) {
                    writer.write(strings[i]); // Write each string without quotes
                }
                writer.newLine(); // Ends the current line to start a new one.
                System.out.println("Strings have been written to the CSV file.");
            } catch (IOException e) {
                System.err.println("Error writing to the file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error creating directories: " + e.getMessage());
        }
    }

    /**
     * The main method to start the simulation.
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) throws Exception {
        // Initialization of simulation parameters.
        int mapSize = 300;
        int nbHumanSus = 19990;
        int nbHumanIll = 10;
        int nbIteration = 730;
        MersenneTwister ran = new MersenneTwister(4357);
        int nbExperiments = 100;
        
        for (int j=0;j<nbExperiments;j++){
            execute exe = new execute(mapSize, nbHumanSus, nbHumanIll,ran);
            ArrayList<String> results = new ArrayList<String>(); 
        
            for (int i = 0; i < nbIteration; i++) {
                results.add(exe.map.debugGlobalStatus());
                exe.map.update(exe.ran);
                updateLoadingBar(i, nbIteration);
            }
    
            updateLoadingBar(nbIteration, nbIteration);
            System.out.println("\nexperiment number: "+(j+1)+" completed");
            writeStringsToCSV("results/SEIR" + (j+1) + ".csv", results.toArray(new String[0]));
        }
        

    }
}
