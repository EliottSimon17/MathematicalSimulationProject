package Simulation;


/* TODO:
 *  - GIVE THE PARAMETERS OF THE POISSON AND TRUNCNORMAL DISTRIBUTIONS IN THE CONSTRUCTOR
 *      --> compute them from the assignment sheet
 *
 *  - WRITE THE BODY OF THE getNewServiceTime() METHOD TO USE THE TRUNCNORMAL
 */

public class Consumer extends Customer {
    // These variables are the distributions used for generating arrival times and service times
    private static Poisson arrivalDistr = new Poisson(param1, param2, ...);
    private static TruncNormal serviceDistr = TruncNormal(param1, param2);

    /**
     * Default constructor with no parameters
     */
    public Consumer() {
        this(new Time(0));
    }

    /**
     * Parametric constructor
     *
     * @param previousArrivalTime the time of the last arrival which happened so far
     */
    public Consumer(Time previousArrivalTime) {
        // Call the superclass constructor (which should be the one from Product
        super();
        // Set the previous arrival time
        arrivalDistr.setPreviousArrivalTime(previousArrivalTime);
    }

    /**
     * This method generates a new arrival time given the current time.
     *
     * @param previousArrivalTime the previous generated arrival time
     *
     * @return a new arrival time, generated according to the arrivalDistr distribution
     */
    public static double getNewArrivalTime(Time previousArrivalTime) {
        // Pass on the previous arrival time to the arrival time distribution
        arrivalDistr.setPreviousArrivalTime(previousArrivalTime);

        // Generate and return a random number
        return arrivalDistr.drawRandom();
    }

    public static double getNewServiceTime() {
        // TODO
    }
}
