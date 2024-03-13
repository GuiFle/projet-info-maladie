package tp.common;
import utility.MersenneTwister;

public class human {

    // Variables d'instance pour le statut et le temps de statut
    private String status;
    private int statusTime;

    // Variables finales pour les durées d'exposition, d'infection et de récupération
    private final int dE;
    private final int dI;
    private final int dR;

    public int x;
    public int y;    
        

    // Constructeur de la classe human
    public human(String status, int statusTime, int dE, int dI, int dR) {
        this.status = status;
        this.dE = dE;
        this.dI = dI;
        this.dR = dR;
        this.statusTime = statusTime;
    }

    public human(String status, MersenneTwister ran) {
        this.status = status;
        this.dE = (int)ran.negExp(3);
        this.dI = (int)ran.negExp(7);
        this.dR = (int)ran.negExp(365);
        this.statusTime = 0;
    }


    public void changePos(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    // Méthode pour afficher l'état actuel de l'humain
    public String toString() {
        return "Status:" + status 
        + " T:" + String.valueOf(statusTime) + " dE:" + String.valueOf(dE)
        + " dI:" + String.valueOf(dI) + " dR:" + String.valueOf(dR);
    }

    // Méthode pour afficher les informations de débogage de l'humain
    public void Debug() {
        System.out.println(toString());
    }

    // Méthode pour récupérer le statut actuel de l'humain
    public String get_status() {
        return status;
    }

    // Méthode pour passer à l'état exposé
    public void becomeExposed() throws Exception {
        // Vérifier si l'humain est déjà exposé
        if (status != "S") {
            throw new Exception("Cannot become Exposed");
        } else {
            status = "E"; // Passer à l'état exposé
        }
    }

    // Méthode pour mettre à jour le statut de l'humain en fonction du temps
    public void update() {
        statusTime += 1; 

        // Vérifier l'état actuel de l'humain et mettre à jour en conséquence
        if (status == "E" && statusTime > dE) {
            status = "I"; 
            statusTime = 0; 
        } else if (status == "I" && statusTime > dI) {
            status = "R"; 
            statusTime = 0; 
        } else if (status == "R" && statusTime > dR) {
            status = "S"; 
            statusTime = 0; 
        }
    }
}
