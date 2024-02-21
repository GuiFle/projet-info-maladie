package tp.common;

public class human {

    private String status;
    private int statusTime;

    private final int dE;
    private final int dI;
    private final int dR;

    public human(String status,int statusTime,int dE,int dI,int dR){
        this.status = status;
        this.dE = dE;
        this.dI = dI;
        this.dR = dR;
        this.statusTime = statusTime;
    }

    public String get_status(){
        return status;
    }

    public void becomeExposed() throws Exception {
        if (status == "R"){
            throw new Exception("Cannot become Exposed");
        }
        else{
            status = "E";
        }
    }

    public void updateStatus(){
        if (status == "E" && statusTime > dE){
            status == "I";
        }
        else if (status == "I" && statusTime > dI){
            status == "R";
        }
        else if (status == "R" && statusTime > dR){
            status == "S";
        }
    }

    public void becomeOlder(){
        statusTime += 1;
    }

}