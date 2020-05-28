package Simulation;

public class Corporate extends Customer {
    // These variables are the distributions used for generating arrival times and service times
    private static final double lambdaStarPerSecond = 1.0 / 60;
    // the maximum value that lambda(t) could possibly take on

    // Set some constants, to get the lambdaT
    private static final double sixPm = new Time(0, 0, 18).toSeconds();
    private static final double eightAm = new Time(0, 0, 8).toSeconds();
    private static final double oneDay = new Time(0, 0, 0, 1).toSeconds();
    private static final LambdaT lambdaGivenTimeInSeconds = (time) -> {
        double timeInSeconds = time.toSeconds();
        double timeInTheDay = timeInSeconds % oneDay;

        if (eightAm <= timeInTheDay && timeInTheDay < sixPm)
            return 1.0/60;      // rate = 1 / minute = 1/60 / second
        else
            return 0.2/60;      // rate = 0.2 / minute = 0.2/60 / second
    };
    
    //all measured in seconds
    private static final double serviceMean = 3.6*60;
    private static final double serviceStd = 1.2*60;
    private static final double serviceMin = 45;
    private static final double serviceMax = Double.NaN;
    
    private static final Poisson arrivalDistr = new Poisson(lambdaStarPerSecond, lambdaGivenTimeInSeconds);
    private static TruncNormal serviceDistr = new TruncNormal(serviceMean, serviceStd, serviceMin, serviceMax);

    /**
     * Default constructor with no parameters
     */
    public Corporate() {
        super();
    }

    /**
     * Parametric constructor
     *
     * @param previousArrivalTime the time of the last arrival which happened so far
     *//* NOT NEEDED
    public Corporate (Time previousArrivalTime) {
        // Call the superclass constructor (which should be the one from Product
        this();
        // Set the previous arrival time
        arrivalDistr.setPreviousArrivalTime(previousArrivalTime);
    }*/

    /**
     * This method generates a new arrival time given the current time.
     *
     * @param previousArrivalTime the previous generated arrival time
     *
     * @return a new arrival time, generated according to the arrivalDistr distribution
     *//* NOT NEEDED
    public static Time getNewArrivalTime(Time previousArrivalTime) {
        // Pass on the previous arrival time to the arrival time distribution
        arrivalDistr.setPreviousArrivalTime(previousArrivalTime);

        // Generate and return a random number
        return arrivalDistr.drawRandom();
    }*/

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
