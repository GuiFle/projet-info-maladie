package tp.common;
import java.util.*;
import tp.common.human;

public class map {

    public ArrayList<human>[][] grid;

    @SuppressWarnings("unchecked")

    public map(int m,int n){
        this.grid = new ArrayList[m][n];
    }

    public void addHuman(int x,int y, human buddy){
        grid[x][y].add(buddy);
    }
    
}
