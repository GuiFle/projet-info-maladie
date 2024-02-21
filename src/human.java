package tp.common;

public class human {

    private String status;
    private int status_time;

    private final int dE;
    private final int dI;
    private final int dR;

    public human(String status,int status_time,int dE,int dI,int dR){
        this.status = status;
        this.dE = dE;
        this.dI = dI;
        this.dR = dR;
        this.status_time = status_time;
    }

    public String get_status(){
        return this.status;
    }

}