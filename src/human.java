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

    public String toString(){
        return "Status:" + status 
        + " T:" + String.valueOf(statusTime) + " dE:" + String.valueOf(dE)
        + " dI:" + String.valueOf(dI) + " dR:" + String.valueOf(dR);
    }

    public void Debug(){
        System.out.println(toString());
    }

    public String get_status(){
        return status;
    }

    public void becomeExposed() throws Exception {
        if (status != "S"){
            throw new Exception("Cannot become Exposed");
        }
        else{
            status = "E";
        }
    }

    public void update(){
        statusTime += 1;
        if (status == "E" && statusTime > dE){
            status = "I";
            statusTime = 0;
        }
        else if (status == "I" && statusTime > dI){
            status = "R";
            statusTime = 0;
        }
        else if (status == "R" && statusTime > dR){
            status = "S";
            statusTime = 0;
        }
    }


}