package tp.common;
import tp.common.human;


public class execute {

    public static void main(String[] args) throws Exception {
        human buddy = new human("S", 0, 0, 0, 0);
        System.out.println(buddy.get_status());
        buddy.becomeInfected();
        System.out.println(buddy.get_status());
    }
}