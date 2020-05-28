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
    private static final double serviceMean = 3.6*60;           // average service time
    private static final double serviceStd = 1.2*60;            // std of the service time
    private static final double serviceMin = 45;                // minimum of the truncated normal
    private static final double serviceMax = Double.NaN;        // maximum (undefined) of the truncated normal
    
    private static final Poisson arrivalDistr = new Poisson(lambdaStarPerSecond, lambdaGivenTimeInSeconds);
    private static TruncNormal serviceDistr = new TruncNormal(serviceMean, serviceStd, serviceMin, serviceMax);

    /**
     * Default constructor with no parameters
     */
    public Corporate() {
        super();
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
