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


public class map {


    public int mapSize;
    public ArrayList<human> buddyArrayWaiting;
    public ArrayList<human> buddyArrayTreated;

    // Grille de liste d'humains
    public ArrayList<human>[][] grid;


    // Constructeur de la classe map
    @SuppressWarnings("unchecked")
    public map(int m) {
        // Initialisation de la grille avec la taille m x m
        this.mapSize = m;
        this.grid = new ArrayList[m][m];
        for(int i=0;i<m;i++){
            for(int j=0;j<m;j++){
                this.grid[i][j] = new ArrayList<human>();
            }
        }
        this.buddyArrayWaiting = new ArrayList<human>();
        this.buddyArrayTreated = new ArrayList<human>();
    }

    public void update(MersenneTwister ran){
        int x,y;
        int n = this.buddyArrayWaiting.size();


        while (n>0) {
            int ranId = ran.nextInt(n);
            human buddy = this.buddyArrayWaiting.get(ranId);

            x = ran.nextInt(mapSize);
            y = ran.nextInt(mapSize);


            move(buddy,x,y);

            if (buddy.get_status() == "S"){

                double p = 1-Math.exp(-0.5*infectedNeighbours(buddy));

                if (ran.nextDouble()<p) {
                    buddy.becomeExposed();
                }
                else{
                    buddy.update();
                }
                
            }
            else{
                buddy.update();
            }
            

            this.buddyArrayTreated.add(buddy);
            this.buddyArrayWaiting.remove(ranId);
            n = this.buddyArrayWaiting.size();
        } 
        ArrayList<human> tmp = this.buddyArrayTreated;
        this.buddyArrayTreated = this.buddyArrayWaiting;
        this.buddyArrayWaiting = tmp;
        
    }

    private void move(human buddy,int x,int y){
        grid[buddy.x][buddy.y].remove(buddy);
        grid[x][y].add(buddy);
        buddy.changePos(x, y);
    }

    private int infectedNeighbours(human buddy){
        int nbInfected = 0;
        int x,y;

        for (int i=0;i<3;i++){
            for (int j=0;i<3;i++){
                x = ((buddy.x-1+i)%mapSize);
                if (x == -1){
                    x = mapSize-1;
                }
                y = ((buddy.y-1+j)%mapSize);
                if (y == -1){
                    y = mapSize-1;
                }
               
             
                for (human bud : grid[x][y]) {
                    if (bud.get_status() == "I") {
                        nbInfected += 1;
                    }
                }
            }
        }

        return nbInfected;
    }

    
    public void addAllHumans(MersenneTwister ran, int nbHuman, String state){

        human buddy;
    
        for(int i = 0; i<nbHuman;i++){
            buddy = new human(state, ran);
            buddyArrayWaiting.add(buddy);
        }

        for (human budd : this.buddyArrayWaiting) {
            addHuman(ran.nextInt(this.mapSize),ran.nextInt(this.mapSize),budd);
        }

    }

    public void debugMap(){
        for(int i=0;i<mapSize;i++){
            for(int j=0;j<mapSize;j++){
                System.out.print(grid[i][j].size()+" ");
            }
            System.out.println();
        }
    }

    

    public String debugGlobalStatus(){
        Integer i=0;
        Integer s=0;
        Integer r=0;
        Integer e=0;

        for (human buddy  : this.buddyArrayWaiting) {
            String state = buddy.get_status();

            switch (state) {
                case "S":
                    s += 1;
                    break;
                case "E":
                    e += 1;
                    break;    
                case "I":
                    i += 1;
                    break;
                case "R":
                    r += 1;
                    break;
                
                default:
                    System.out.println("error state");
                    break;
            }
        }
        
        return s +"," + e +"," + i + "," + r;
    }

    // Méthode pour ajouter un humain à la position (x, y) de la grille
    public void addHuman(int x, int y, human buddy) {
        grid[x][y].add(buddy); 
        buddy.changePos(x, y);
    }
}
