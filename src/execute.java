package tp.common;
import java.util.ArrayList;
import utility.MersenneTwister;
import tp.common.*;


public class execute {

    private MersenneTwister ran;
    private map map;
    private int nbHuman;

    public execute(){
        this.ran = new MersenneTwister(4357);
        this.map = new map(10);
        this.nbHuman = 100;
    }

    public static void main(String[] args) throws Exception {

        execute exe = new execute();
        ArrayList<human> buddyArray = new ArrayList<human>();
        human buddy;

        for(int i = 0; i<exe.nbHuman;i++){
            buddy = new human("S", exe.ran);
            buddyArray.add(buddy);
        }

        for (human budd : buddyArray) {

            exe.map.addHuman(exe.ran.nextInt(exe.map.mapSize),exe.ran.nextInt(exe.map.mapSize),budd);
        }

        for(int i=0;i<exe.map.mapSize;i++){
            for(int j=0;j<exe.map.mapSize;j++){
                System.out.println(exe.map.grid[i][j].size());
                
            }
        }

        
        


        
        

    }
}