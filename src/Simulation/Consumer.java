package Simulation;


/* TODO:
 *  - GIVE THE PARAMETERS OF THE POISSON AND TRUNCNORMAL DISTRIBUTIONS IN THE CONSTRUCTOR
 *      --> compute them from the assignment sheet
 *
 *  - WRITE THE BODY OF THE getNewServiceTime() METHOD TO USE THE TRUNCNORMAL
 */

public class Consumer extends Customer {
    // These variables are the distributions used for generating arrival times and service times
    private static final double lambdaStarPerSecond = 3.8 / 60;        // Since lambda(t) has mean 2 and lowest point 0.2,
    // the highest point will be 2 + (2-0.2) = 2 + 1.8 = 3.8 / minute = 3.8/60 / second

    // Set some constants, to get the lambdaT
    private static final double period = new Time(0, 0, 0, 1).toSeconds();
    private static final double lowestPoint = new Time(0, 0, 3).toSeconds();
    private static final double mean = 2.0 / 60;      //mean arrival rate is 2 / minute, = 2/60 / second
    private static final double range = 3.6 / 60;
    private static final LambdaT lambdaGivenTimeInSeconds = (time) -> {
        double timeInSeconds = time.toSeconds();

        // formula should be:
        // (max-min)/2 * cos((2*pi*(x + (period/2 - wishedLowestPoint)))/period) + mean
        return range/2 * Math.cos((2.0*Math.PI*(timeInSeconds + (period/2 - lowestPoint)))/(period)) + mean;
    };
    
    //in seconds
    private static final double serviceMean = 1.2*60;
    private static final double serviceStd = 35;
    private static final double serviceMin = 25;
    private static final double serviceMax = Double.NaN;
    
    private static final Poisson arrivalDistr = new Poisson(lambdaStarPerSecond, lambdaGivenTimeInSeconds);
    private static TruncNormal serviceDistr = new TruncNormal(serviceMean, serviceStd, serviceMin, serviceMax);

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
    public static Time getNewArrivalTime(Time previousArrivalTime) {
        // Pass on the previous arrival time to the arrival time distribution
        arrivalDistr.setPreviousArrivalTime(previousArrivalTime);

        // Generate and return a random number
        return arrivalDistr.drawRandom();
    }

    /**
     * This method generates a new arrival time given the current time.
     * It re-uses the previous arrival time
     *
     * @return
     */
    public static Time getNewArrivalTime() {
        //Generate and return a random number
        return arrivalDistr.drawRandom();
    }

    public static Time getNewServiceTime() {
        return serviceDistr.drawRandom();
    }

    /**
     * Resets the arrivalDistr's previousArrivalTime
     */
    public static void resetPreviousArrivalTime() {
        arrivalDistr.setPreviousArrivalTime(new Time(0));
    }
}
