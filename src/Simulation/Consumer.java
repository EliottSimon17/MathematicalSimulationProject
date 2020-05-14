package Simulation;


/* TODO:
 *  - GIVE THE PARAMETERS OF THE POISSON AND TRUNCNORMAL DISTRIBUTIONS IN THE CONSTRUCTOR
 *      --> compute them from the assignment sheet
 *
 *  - CHANGE TO USE TIME OBJECTS INSTEAD OF A DOUBLE VALUE
 *
 *  - WRITE THE BODY OF THE getNewServiceTime() METHOD TO USE THE TRUNCNORMAL
 */

public class Consumer extends Customer {
    private Poisson arrivalDistr = new Poisson(param1, param2, ...);
    private TruncNormal serviceDistr = TruncNormal(param1, param2);

    /**
     * Default constructor with no parameters
     */
    public Consumer() {
        this(0);
    }

    /**
     * Parametric constructor
     *
     * @param previousArrivalTime the time of the last arrival which happened so far
     */
    public Consumer(double previousArrivalTime) {
        // Set the previous arrival time
        arrivalDistr.setPreviousArrivalTime(previousArrivalTime);
    }

    public static double getNewArrivalTime(double currentTime) {
        // Give the current time
        arrivalDistr.setCurrentTime(currentTime);
        // Draw a random number
        arrivalDistr.drawRandom();
    }

    public static double getNewServiceTime() {
        // TODO
    }
}
