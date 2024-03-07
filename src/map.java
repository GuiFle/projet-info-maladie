package tp.common;
import java.util.*;
import tp.common.human;

import java.util.ArrayList;


public class map {

    // Grille de liste d'humains
    public ArrayList<human>[][] grid;

    // Constructeur de la classe map
    @SuppressWarnings("unchecked")
    public map(int m, int n) {
        // Initialisation de la grille avec la taille m x n
        this.grid = new ArrayList[m][n];
    }

    // Méthode pour ajouter un humain à la position (x, y) de la grille
    public void addHuman(int x, int y, human buddy) {
        grid[x][y].add(buddy); 
    }
}
