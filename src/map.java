package tp.common;
import java.util.*;
import tp.common.human;

import java.util.ArrayList;


public class map {


    public int mapSize;

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
    }
    
    /*public int[] GetGlobalStatus(){
        int[] resultArray;
        return resultArray;

    }*/

    // Méthode pour ajouter un humain à la position (x, y) de la grille
    public void addHuman(int x, int y, human buddy) {
        grid[x][y].add(buddy); 
    }
}
