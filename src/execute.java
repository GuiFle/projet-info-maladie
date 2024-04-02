package tp.common;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import utility.MersenneTwister;
import tp.common.*;


public class execute {

    private MersenneTwister ran;
    private map map;
    

    public execute(int mapSize, int nbHumanSus, int nbHumanIll, MersenneTwister ran){
        this.ran = ran;
        this.map = new map(mapSize);
        map.addAllHumans(ran,nbHumanSus,"S");
        map.addAllHumans(ran,nbHumanIll,"I");

    }

    public static void updateLoadingBar(int currentProgress, int total) {
        int barLength = 50; // Length of the loading bar
        int progress = (int) ((float) currentProgress / total * barLength);
        StringBuilder progressBar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < progress) {
                progressBar.append("=");
            } else {
                progressBar.append(" ");
            }
        }
        progressBar.append("] ");
        System.out.print("\r" + progressBar.toString() + (progress * 2) + "%");
    }
    
    
    public static void writeStringsToCSV(String fileName, String[] strings) {
        Path filePath = Paths.get(fileName);
    
        try {
            // Create directories if they don't exist
            Files.createDirectories(filePath.getParent());
    
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                for (int i = 0; i < strings.length; i++) {
                    writer.write(strings[i]); // Write each string without quotes
                }
                writer.newLine(); // Start a new line for the next row
                System.out.println("Strings have been written to the CSV file.");
            } catch (IOException e) {
                System.err.println("Error writing to the file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error creating directories: " + e.getMessage());
        }
    }
    
    


    public static void main(String[] args) throws Exception {
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