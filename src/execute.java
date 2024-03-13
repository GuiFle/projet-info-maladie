package tp.common;

import tp.common.*;
import utility.MersenneTwister;


public class execute {

    public static void main(String[] args) throws Exception {
        human buddy = new human("S", 0, 7, 9, 8);
        buddy.becomeExposed();
        buddy.Debug();

        MersenneTwister ran = new MersenneTwister(12546);

        /*for(int i = 0;i <40; i++){
        buddy.update();
        buddy.Debug();
        }*/

    }
}