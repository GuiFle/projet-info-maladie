package tp.common;
import utility.MersenneTwister;

/**
 * The human class represents an individual in the disease propagation simulation.
 */
public class human {

    // Health status of the human ('S' for susceptible, 'E' for exposed, 'I' for infected, 'R' for recovered)
    private String status;
    // Time elapsed since the last status change
    private int statusTime;

    // Duration of the exposure period
    private final int dE;
    // Duration of the infection period
    private final int dI;
    // Duration of the recovery period
    private final int dR;

    // Position of the human on the grid (x and y coordinates)
    public int x;
    public int y;    

    /**
     * Constructor to initialize a human with a specific status and durations.
     * @param status Initial status of the human.
     * @param statusTime Time elapsed in the current status.
     * @param dE Duration of the exposure period.
     * @param dI Duration of the infection period.
     * @param dR Duration of the recovery period.
     */
    public human(String status, int statusTime, int dE, int dI, int dR) {
        this.status = status;
        this.dE = dE;
        this.dI = dI;
        this.dR = dR;
        this.statusTime = statusTime;
    }

    /**
     * Constructor to initialize a human with a status and randomly generated durations.
     * @param status Initial status of the human.
     * @param ran Random number generator to determine the durations.
     */
    public human(String status, MersenneTwister ran) {
        this.status = status;
        this.dE = (int)ran.negExp(3);
        this.dI = (int)ran.negExp(7);
        this.dR = (int)ran.negExp(365);
        this.statusTime = 0;
    }

    /**
     * Changes the position of the human on the grid.
     * @param x The new horizontal position of the human.
     * @param y The new vertical position of the human.
     */
    public void changePos(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * Returns a string describing the current state of the human.
     * @return Description of the human's state.
     */
    @Override
    public String toString() {
        return "Status:" + status 
        + " T:" + statusTime + " dE:" + dE
        + " dI:" + dI + " dR:" + dR;
    }

    /**
     * Prints debugging information about the human to the console.
     */
    public void Debug() {
        System.out.println(toString());
    }

    /**
     * Retrieves the current status of the human.
     * @return The current status of the human.
     */
    public String get_status() {
        return status;
    }

    /**
     * Transitions the human to the exposed status if they are susceptible.
     */
    public void becomeExposed() {
        if (!"S".equals(status)) {
            System.out.println("Error: Cannot become Exposed");
        } else {
            status = "E"; // Transition to exposed
        }
    }

    /**
     * Updates the status of the human based on the elapsed time.
     */
    public void update() {
        statusTime += 1; // Increment the status time

        // Update status based on the elapsed time and the periods of each phase
        if ("E".equals(status) && statusTime > dE) {
            status = "I"; // Transition to infected
            statusTime = 0; 
        } else if ("I".equals(status) && statusTime > dI) {
            status = "R"; // Transition to recovered
            statusTime = 0; 
        } else if ("R".equals(status) && statusTime > dR) {
            status = "S"; // Return to susceptible
            statusTime = 0; 
        }
    }
}
