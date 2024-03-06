package tp.common;
import tp.common.human;
import tp.common.map;
import java.lang.Math;


public class execute {

    private static double negExp(double inMean, MTRandom ran){
        return -inMean * Math.log(1 - ran.nextDouble(1));
    }

    private static void areYouSickNow(human buddy,MTRandom ran){
        
    }

    public static void main(String[] args) throws Exception {

        MTRandom ran = new MTRandom(9876);
        
        human buddy = new human("S", 0, negExp(3,ran), negExp(7,ran), negExp(365,ran));
        buddy.becomeExposed();
        buddy.Debug();

        map grid = new map(5,3);

        /*for(int i = 0;i <40; i++){
        buddy.update();
        buddy.Debug();
        }*/

    }
}
