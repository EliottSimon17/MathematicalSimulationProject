package Simulation;


public class Consumer extends Customer {
    // These variables are the distributions used for generating arrival times and service times
    private static final double lambdaStarPerSecond = 3.8 / 60;        // Since lambda(t) has mean 2 and lowest point 0.2,
    // the highest point will be 2 + (2-0.2) = 2 + 1.8 = 3.8 / minute = 3.8/60 / second

    // Set some constants, to get the lambdaT
    private static final double period = new Time(0, 0, 0, 1).toSeconds();     // period is obviously 1 day
    private static final double lowestPoint = new Time(0, 0, 3).toSeconds();        // lowest rate is at 3 am
    private static final double mean = 2.0 / 60;      // mean arrival rate is 2 / minute, = 2/60 / second
    private static final double range = 3.6 / 60;     // the range goes from 0.2 to 2 + (2-0.2) = 3.8 --> range of 3.6 /minutes
    private static final LambdaT lambdaGivenTimeInSeconds = (time) -> {
        double timeInSeconds = time.toSeconds();

        // formula should be:
        // (max-min)/2 * cos((2*pi*(x + (period/2 - wishedLowestPoint)))/period) + mean
        return range/2 * Math.cos((2.0*Math.PI*(timeInSeconds + (period/2 - lowestPoint)))/(period)) + mean;
    };
    
    //all measures in seconds
    private static final double serviceMean = 1.2*60;       // average service time
    private static final double serviceStd = 35;            // std of the service time
    private static final double serviceMin = 25;            // minimum of the truncated normal
    private static final double serviceMax = Double.NaN;    // maximum (undefined) of the truncated normal

    // The distribution objects that will be sampled from
    private static final Poisson arrivalDistr = new Poisson(lambdaStarPerSecond, lambdaGivenTimeInSeconds);
    private static TruncNormal serviceDistr = new TruncNormal(serviceMean, serviceStd, serviceMin, serviceMax);

    /**
     * Default constructor with no parameters
     */
    public Consumer() {
        super();
    }

    /**
     * This method generates a new arrival time given the current time.
     * It re-uses the previous arrival time
     *
     * @return a new generated arrival time of Consumers
     */
    public static Time getNewArrivalTime() {
        //Generate and return a random number
        return arrivalDistr.drawRandom();
    }

    /**
     * Generate a new service time
     *
     * @return a new generated service time of Consumers
     */
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
