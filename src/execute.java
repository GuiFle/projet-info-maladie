package tp.common;
import tp.common.human;


public class execute {

    public static void main(String[] args) throws Exception {
        human buddy = new human("S", 0, 7, 9, 8);
        buddy.becomeExposed();
        buddy.Debug();

        MTRandom ran = new MTRandom();

        /*for(int i = 0;i <40; i++){
        buddy.update();
        buddy.Debug();t
        }*/

    }
}
